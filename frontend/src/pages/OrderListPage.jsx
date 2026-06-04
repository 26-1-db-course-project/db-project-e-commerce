import { useEffect, useState, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { orderApi } from '../api';
import { useMember } from '../context/MemberContext';
import { formatPrice } from '../constants';
import { Loading, ErrorBox, Empty } from '../components/common';

function defaultStartDate() {
  const d = new Date();
  d.setFullYear(d.getFullYear() - 1);
  return d.toISOString().slice(0, 10);
}

export default function OrderListPage() {
  const { member } = useMember();
  const [startDate, setStartDate] = useState(defaultStartDate);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = useCallback(() => {
    if (!member) return;
    setLoading(true);
    setError(null);
    // 백엔드는 ISO LocalDateTime 형식을 기대한다.
    orderApi
      .list(member.memberId, `${startDate}T00:00:00`)
      .then((d) => setOrders(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [member, startDate]);

  useEffect(() => {
    load();
  }, [load]);

  if (!member) {
    return (
      <div className="page">
        <Empty message="로그인 후 주문내역을 확인할 수 있습니다." />
        <div className="center"><Link to="/login" className="btn-primary">로그인</Link></div>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="page-head">
        <h1>주문내역</h1>
        <label className="field inline">
          조회 시작일
          <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
        </label>
      </div>

      {loading ? (
        <Loading />
      ) : error ? (
        <ErrorBox message={error} />
      ) : orders.length === 0 ? (
        <Empty message="주문내역이 없습니다." />
      ) : (
        <ul className="order-list">
          {orders.map((o) => (
            <li key={o.orderId} className="order-card">
              <Link to={`/orders/${o.orderId}`} className="order-card-link">
                <div className="order-card-head">
                  <span className="order-id">주문 #{o.orderId}</span>
                  <span className="order-date">
                    {o.orderDate ? new Date(o.orderDate).toLocaleString('ko-KR') : '-'}
                  </span>
                </div>
                <div className="order-title">{o.orderSummaryTitle}</div>
                <div className="order-card-foot">
                  <span className="order-address">{o.shippingAddress}</span>
                  <span className="order-total">{formatPrice(o.totalPrice)}</span>
                </div>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
