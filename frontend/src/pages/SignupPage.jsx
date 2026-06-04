import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { memberApi } from '../api';
import { useMember } from '../context/MemberContext';
import { MEMBER_ROLES } from '../constants';

const initial = {
  loginId: '',
  email: '',
  password: '',
  phoneNumber: '',
  role: 'ROLE_CUSTOMER',
  city: '',
  district: '',
  detail: '',
};

export default function SignupPage() {
  const { setMember } = useMember();
  const navigate = useNavigate();
  const [form, setForm] = useState(initial);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const onChange = (e) => setForm((f) => ({ ...f, [e.target.name]: e.target.value }));

  const submit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      // CreateMemberRequest 구조에 맞춰 address 를 중첩 객체로 구성한다.
      const request = {
        loginId: form.loginId,
        email: form.email,
        password: form.password,
        phoneNumber: form.phoneNumber,
        role: form.role,
        address: {
          city: form.city,
          district: form.district,
          detail: form.detail,
        },
      };
      const res = await memberApi.create(request);
      setMember({ memberId: res.id, loginId: res.loginId });
      alert(res.message || '회원가입이 완료되었습니다.');
      navigate('/');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page auth-page">
      <div className="auth-card wide">
        <h1>회원가입</h1>
        <form onSubmit={submit} className="form-grid">
          <label className="field">아이디
            <input name="loginId" value={form.loginId} onChange={onChange} required />
          </label>
          <label className="field">비밀번호
            <input name="password" type="password" value={form.password} onChange={onChange} required />
          </label>
          <label className="field">이메일
            <input name="email" type="email" value={form.email} onChange={onChange} required />
          </label>
          <label className="field">전화번호
            <input name="phoneNumber" value={form.phoneNumber} onChange={onChange} placeholder="010-0000-0000" required />
          </label>
          <label className="field">회원 유형
            <select name="role" value={form.role} onChange={onChange}>
              {MEMBER_ROLES.map((r) => (
                <option key={r.value} value={r.value}>{r.label}</option>
              ))}
            </select>
          </label>
          <div className="field-spacer" />

          <label className="field">시/도
            <input name="city" value={form.city} onChange={onChange} placeholder="서울" required />
          </label>
          <label className="field">구/군
            <input name="district" value={form.district} onChange={onChange} placeholder="강남구" required />
          </label>
          <label className="field full">상세주소
            <input name="detail" value={form.detail} onChange={onChange} placeholder="테헤란로 123 101호" required />
          </label>

          {error && <p className="msg-error full">{error}</p>}
          <button className="btn-primary block full" type="submit" disabled={loading}>
            {loading ? '가입 중...' : '가입하기'}
          </button>
        </form>
        <p className="auth-foot">
          이미 계정이 있으신가요? <Link to="/login">로그인</Link>
        </p>
      </div>
    </div>
  );
}
