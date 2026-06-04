import { useEffect, useState, useCallback } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { memberApi } from '../api';
import { useMember } from '../context/MemberContext';
import { Loading, ErrorBox, Empty } from '../components/common';

export default function ProfilePage() {
  const { member, logout } = useMember();
  const navigate = useNavigate();

  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [addr, setAddr] = useState({ city: '', district: '', detail: '' });
  const [msg, setMsg] = useState(null);

  const load = useCallback(() => {
    if (!member) return;
    setLoading(true);
    memberApi
      .get(member.memberId)
      .then((d) => {
        setProfile(d);
        if (d.address) {
          setAddr({
            city: d.address.city || '',
            district: d.address.district || '',
            detail: d.address.detail || '',
          });
        }
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [member]);

  useEffect(() => {
    load();
  }, [load]);

  if (!member) {
    return (
      <div className="page">
        <Empty message="로그인이 필요합니다." />
        <div className="center"><Link to="/login" className="btn-primary">로그인</Link></div>
      </div>
    );
  }
  if (loading) return <div className="page"><Loading /></div>;
  if (error) return <div className="page"><ErrorBox message={error} /></div>;

  const onAddr = (e) => setAddr((a) => ({ ...a, [e.target.name]: e.target.value }));

  // 기존 주소가 있으면 수정(PATCH), 없으면 추가(POST)
  const saveAddress = async (e) => {
    e.preventDefault();
    setMsg(null);
    try {
      if (profile.address?.addressId) {
        await memberApi.updateAddress(member.memberId, profile.address.addressId, {
          address_id: profile.address.addressId,
          city: addr.city,
          district: addr.district,
          detail: addr.detail,
        });
        setMsg({ type: 'success', text: '주소가 수정되었습니다.' });
      } else {
        await memberApi.addAddress(member.memberId, addr);
        setMsg({ type: 'success', text: '주소가 추가되었습니다.' });
      }
      load();
    } catch (err) {
      setMsg({ type: 'error', text: err.message });
    }
  };

  const addNewAddress = async () => {
    setMsg(null);
    try {
      await memberApi.addAddress(member.memberId, addr);
      setMsg({ type: 'success', text: '새 주소가 추가되었습니다.' });
      load();
    } catch (err) {
      setMsg({ type: 'error', text: err.message });
    }
  };

  const withdraw = async () => {
    if (!confirm('정말 회원 탈퇴하시겠습니까?')) return;
    try {
      await memberApi.remove(member.memberId);
      alert('회원 탈퇴가 완료되었습니다.');
      logout();
      navigate('/');
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div className="page narrow">
      <h1>내 정보</h1>

      <section className="card">
        <div className="kv"><span>회원번호</span><span>{profile.memberId}</span></div>
        <div className="kv"><span>아이디</span><span>{profile.loginId}</span></div>
        <div className="kv"><span>이메일</span><span>{profile.email}</span></div>
        <div className="kv"><span>전화번호</span><span>{profile.phoneNumber}</span></div>
        <div className="kv"><span>회원등급</span><span>{profile.memberGrade}</span></div>
        <div className="kv"><span>활동상태</span><span>{profile.activityStatus}</span></div>
      </section>

      <section className="card">
        <h3>배송지</h3>
        {profile.address ? (
          <p className="address-line">
            [{profile.address.addressId}] {profile.address.city} {profile.address.district}{' '}
            {profile.address.detail}
          </p>
        ) : (
          <p className="hint">등록된 배송지가 없습니다.</p>
        )}

        <form className="form-grid" onSubmit={saveAddress}>
          <label className="field">시/도
            <input name="city" value={addr.city} onChange={onAddr} required />
          </label>
          <label className="field">구/군
            <input name="district" value={addr.district} onChange={onAddr} required />
          </label>
          <label className="field full">상세주소
            <input name="detail" value={addr.detail} onChange={onAddr} required />
          </label>
          {msg && <p className={`full ${msg.type === 'error' ? 'msg-error' : 'msg-success'}`}>{msg.text}</p>}
          <div className="full button-row">
            <button className="btn-primary" type="submit">
              {profile.address?.addressId ? '주소 수정' : '주소 등록'}
            </button>
            <button className="btn-outline" type="button" onClick={addNewAddress}>
              새 주소 추가
            </button>
          </div>
        </form>
      </section>

      <button className="btn-text danger" onClick={withdraw}>회원 탈퇴</button>
    </div>
  );
}