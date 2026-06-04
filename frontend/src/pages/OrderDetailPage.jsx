import { useEffect, useState, useCallback } from 'react';
import { useParams, Link } from 'react-router-dom';
import { orderApi } from '../api';
import { useMember } from '../context/MemberContext';
import { formatPrice } from '../constants';
import { Loading, ErrorBox, Empty } from '../components/common';

export default function OrderDetailPage() {
  const { orderId } = useParams();
  const { member } = useMember();
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cancelMsg, setCancelMsg] = useState(null);

  const load = useCallback(() => {
    if (!member) return;
    setLoading(true);
    setError(null);
    orderApi
      .get(orderId, member.memberId)
      .then(setOrder)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [orderId, member]);

  useEffect(() => {
    load();
  }, [load]);

  if (!member) return <div className="page"><Empty message="로그인이 필요합니다." /></div>;
  if (loading) return <div className="page"><Loading /></div>;
  if (error) return <div className="page"><ErrorBox message={error} /></div>;
  if (!order) return null;

  const alreadyCancelled =
    (order.orderItems || []).length > 0 &&
    order.orderItems.every((it) => it.statusName === '주문취소');

  const cancel = async () => {
    if (!confirm('정말 주문을 취소하시겠습니까?')) return;
    setCancelMsg(null);
    try {
      const msg = await orderApi.cancel(orderId, member.memberId);
      setCancelMsg({ type: 'success', text: msg || '주문이 취소되었습니다.' });
      load();
    } catch (e) {
      setCancelMsg({ type: 'error', text: e.message });
    }
  };

  return (
    <div className="page narrow">
      <Link to="/orders" className="back-link">← 주문내역</Link>
      <h1>주문 #{order.orderId}</h1>

      <section className="card">
        <div className="kv"><span>주문일시</span><span>{order.orderDate ? new Date(order.orderDate).toLocaleString('ko-KR') : '-'}</span></div>
        <div className="kv"><span>배송지</span><span>{order.shippingAddress}</span></div>
        <div className="kv"><span>총 결제금액</span><span className="strong">{formatPrice(order.totalPrice)}</span></div>
      </section>

      <section className="card">
        <h3>주문 상품</h3>
        <ul className="order-item-list">
          {(order.orderItems || []).map((it) => (
            <li key={it.productDetailId} className="order-item">
              <div className="order-item-main">
                <span className="order-item-name">{it.productName}</span>
                <span className="order-item-sku">SKU #{it.productDetailId}</span>
              </div>
              <span className="status-badge">{it.statusName}</span>
              <span className="order-item-qty">x{it.quantity}</span>
              <span className="order-item-price">{formatPrice(it.orderPrice)}</span>
            </li>
          ))}
        </ul>
      </section>

      {alreadyCancelled ? (
        <div className="result-banner error">이미 취소된 주문입니다.</div>
      ) : (
        <button className="btn-outline danger block" onClick={cancel}>주문 취소</button>
      )}
      {cancelMsg && (
        <div className={`result-banner ${cancelMsg.type}`}>{cancelMsg.text}</div>
      )}
    </div>
  );
}
