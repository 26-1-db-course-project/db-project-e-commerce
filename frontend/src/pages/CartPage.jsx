import { useEffect, useState, useCallback } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { cartApi } from '../api';
import { useMember } from '../context/MemberContext';
import { formatPrice } from '../constants';
import { Loading, ErrorBox, Empty } from '../components/common';

export default function CartPage() {
  const { member } = useMember();
  const navigate = useNavigate();

  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selected, setSelected] = useState(() => new Set());

  const load = useCallback(() => {
    if (!member) return;
    setLoading(true);
    setError(null);
    cartApi
      .get(member.memberId)
      .then((d) => {
        setCart(d);
        setSelected(new Set((d.cartItems || []).map((i) => i.productDetailId)));
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
        <Empty message="로그인 후 장바구니를 이용할 수 있습니다." />
        <div className="center"><Link to="/login" className="btn-primary">로그인</Link></div>
      </div>
    );
  }

  if (loading) return <div className="page"><Loading /></div>;
  if (error) return <div className="page"><ErrorBox message={error} /></div>;

  const items = cart?.cartItems || [];

  const toggle = (id) => {
    setSelected((prev) => {
      const next = new Set(prev);
      next.has(id) ? next.delete(id) : next.add(id);
      return next;
    });
  };

  const changeQty = async (item, qty) => {
    if (qty < 1) return;
    try {
      await cartApi.updateQuantity(member.memberId, item.productDetailId, qty);
      load();
    } catch (e) {
      alert(e.message);
    }
  };

  const removeItem = async (item) => {
    try {
      await cartApi.removeItem(member.memberId, item.productDetailId);
      load();
    } catch (e) {
      alert(e.message);
    }
  };

  const selectedTotal = items
    .filter((i) => selected.has(i.productDetailId))
    .reduce((sum, i) => sum + i.price * i.quantity, 0);

  const goCheckout = () => {
    const ids = items.filter((i) => selected.has(i.productDetailId)).map((i) => i.productDetailId);
    if (ids.length === 0) return alert('주문할 상품을 선택하세요.');
    navigate('/checkout', { state: { selectedProductDetailIds: ids } });
  };

  return (
    <div className="page">
      <h1>장바구니</h1>

      {items.length === 0 ? (
        <Empty message="장바구니가 비어 있습니다." />
      ) : (
        <div className="cart-layout">
          <ul className="cart-items">
            {items.map((item) => (
              <li key={item.productDetailId} className="cart-item">
                <input
                  type="checkbox"
                  checked={selected.has(item.productDetailId)}
                  onChange={() => toggle(item.productDetailId)}
                />
                <div className="cart-item-info">
                  <span className="cart-item-name">{item.productName}</span>
                  <span className="cart-item-option">
                    {item.optionValue || `SKU #${item.productDetailId}`}
                  </span>
                  <span className="cart-item-price">{formatPrice(item.price)}</span>
                </div>
                <div className="qty-control">
                  <button onClick={() => changeQty(item, item.quantity - 1)}>−</button>
                  <span>{item.quantity}</span>
                  <button onClick={() => changeQty(item, item.quantity + 1)}>+</button>
                </div>
                <span className="cart-item-subtotal">
                  {formatPrice(item.price * item.quantity)}
                </span>
                <button className="btn-text danger" onClick={() => removeItem(item)}>
                  삭제
                </button>
              </li>
            ))}
          </ul>

          <aside className="cart-summary">
            <h3>주문 요약</h3>
            <div className="summary-row">
              <span>장바구니 합계</span>
              <span>{formatPrice(cart.totalPrice)}</span>
            </div>
            <div className="summary-row total">
              <span>선택 상품 합계</span>
              <span>{formatPrice(selectedTotal)}</span>
            </div>
            <button className="btn-primary block" onClick={goCheckout}>
              선택 상품 주문하기
            </button>
          </aside>
        </div>
      )}
    </div>
  );
}
