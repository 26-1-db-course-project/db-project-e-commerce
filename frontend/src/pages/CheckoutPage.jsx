import { useEffect, useState } from 'react';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import { memberApi, orderApi } from '../api';
import { useMember } from '../context/MemberContext';
import { Loading, ErrorBox, Empty } from '../components/common';

export default function CheckoutPage() {
  const { member } = useMember();
  const navigate = useNavigate();
  const location = useLocation();
  const selectedProductDetailIds = location.state?.selectedProductDetailIds || [];

  const [profile, setProfile] = useState(null);
  const [addressId, setAddressId] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [result, setResult] = useState(null);

  useEffect(() => {
    if (!member) return;
    memberApi
      .get(member.memberId)
      .then((d) => {
        setProfile(d);
        if (d.address?.addressId) setAddressId(String(d.address.addressId));
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [member]);

  if (!member) return <div className="page"><Empty message="로그인이 필요합니다." /></div>;
  if (loading) return <div className="page"><Loading /></div>;
  if (error) return <div className="page"><ErrorBox message={error} /></div>;

  if (selectedProductDetailIds.length === 0) {
    return (
      <div className="page">
        <Empty message="주문할 상품이 없습니다." />
        <div className="center"><Link to="/cart" className="btn-primary">장바구니로</Link></div>
      </div>
    );
  }

  const submit = async () => {
    setResult(null);
    if (!addressId) return setResult({ type: 'error', text: '배송지 주소 ID를 입력하세요.' });
    setSubmitting(true);
    try {
      const message = await orderApi.create({
        memberId: member.memberId,
        deliveryAddressId: Number(addressId),
        selectedProductDetailIds,
      });
      setResult({ type: 'success', text: message || '주문이 완료되었습니다.' });
    } catch (e) {
      setResult({ type: 'error', text: e.message });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="page narrow">
      <h1>주문/결제</h1>

      <section className="card">
        <h3>배송지</h3>
        {profile?.address ? (
          <p className="address-line">
            [{profile.address.addressId}] {profile.address.city} {profile.address.district}{' '}
            {profile.address.detail}
          </p>
        ) : (
          <p className="hint">등록된 배송지가 없습니다. 프로필에서 주소를 추가하세요.</p>
        )}
        <label className="field">
          배송지 주소 ID
          <input
            type="number"
            min="1"
            value={addressId}
            onChange={(e) => setAddressId(e.target.value)}
            placeholder="deliveryAddressId"
          />
        </label>
      </section>

      <section className="card">
        <h3>주문 상품</h3>
        <ul className="checkout-items">
          {selectedProductDetailIds.map((id) => (
            <li key={id}>상품상세(SKU) #{id}</li>
          ))}
        </ul>
        <p className="hint">
          주문 가능 여부와 금액은 서버 프로시저가 검증하여 결과 메시지로 반환합니다.
        </p>
      </section>

      <button className="btn-primary block lg" onClick={submit} disabled={submitting}>
        {submitting ? '주문 처리 중...' : '주문하기'}
      </button>

      {result && (
        <div className={`result-banner ${result.type}`}>
          {result.text}
          {result.type === 'success' && (
            <div className="center" style={{ marginTop: 12 }}>
              <Link to="/orders" className="btn-text">주문내역 보기 →</Link>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
