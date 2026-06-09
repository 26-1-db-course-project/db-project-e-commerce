import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { productApi, reviewApi, cartApi, productDetailApi } from '../api';
import { useMember } from '../context/MemberContext';
import { formatPrice } from '../constants';
import { Loading, ErrorBox, Empty, Stars, ProductThumb } from '../components/common';

export default function ProductDetailPage() {
  const { productId } = useParams();
  const { member } = useMember();

  const [product, setProduct] = useState(null);
  const [optionGroups, setOptionGroups] = useState([]);
  const [details, setDetails] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 옵션 그룹별 선택값 { 옵션타입명: optionDetailId } 과 수량
  const [selectedOptions, setSelectedOptions] = useState({});
  const [productDetailId, setProductDetailId] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [cartMsg, setCartMsg] = useState(null);

  const selectedDetail = details.find((d) => String(d.productDetailId) === String(productDetailId));

  // SKU(detail)가 주어진 옵션 선택과 모두 일치하는지
  const skuMatches = (detail, selection) =>
    Object.entries(selection).every(([type, odId]) =>
      (detail.options || []).some(
        (o) => o.optionTypeName === type && String(o.optionDetailId) === String(odId)
      )
    );

  // 특정 옵션 값이 '다른 그룹의 현재 선택'과 함께 재고 있는 SKU를 만들 수 있는지
  const isValueAvailable = (typeName, odId) => {
    const trial = { ...selectedOptions, [typeName]: odId };
    return details.some((d) => d.stockQuantity > 0 && skuMatches(d, trial));
  };

  const pickOption = (typeName, odId) => {
    setCartMsg(null);
    setSelectedOptions((prev) => {
      // 이미 선택된 옵션을 다시 누르면 선택 취소
      if (String(prev[typeName]) === String(odId)) {
        const { [typeName]: _removed, ...rest } = prev;
        return rest;
      }
      return { ...prev, [typeName]: odId };
    });
  };

  // 모든 그룹이 선택되면 일치하는 SKU 를 찾아 productDetailId 로 확정
  useEffect(() => {
    if (optionGroups.length === 0) return;
    const allSelected = optionGroups.every((g) => selectedOptions[g.optionTypeName]);
    if (!allSelected) {
      setProductDetailId('');
      return;
    }
    const matched = details.find(
      (d) => (d.options || []).length === optionGroups.length && skuMatches(d, selectedOptions)
    );
    setProductDetailId(matched ? String(matched.productDetailId) : '');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedOptions, optionGroups, details]);

  const loadReviews = () => {
    reviewApi
      .listByProduct(productId)
      .then((d) => setReviews(d.reviewList || []))
      .catch(() => setReviews([]));
  };

  useEffect(() => {
    let active = true;
    setLoading(true);
    setError(null);

    Promise.all([
      productApi.get(productId),
      productApi.options(productId).catch(() => ({ optionGroups: [] })),
      productDetailApi.listByProduct(productId).catch(() => ({ productDetailResponseList: [] })),
    ])
      .then(([p, opts, detailList]) => {
        if (!active) return;
        setProduct(p);
        setOptionGroups(opts.optionGroups || []);
        setDetails(detailList.productDetailResponseList || []);
      })
      .catch((e) => active && setError(e.message))
      .finally(() => active && setLoading(false));

    loadReviews();
    return () => {
      active = false;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [productId]);

  const addToCart = async () => {
    setCartMsg(null);
    if (!member) {
      setCartMsg({ type: 'error', text: '로그인이 필요합니다.' });
      return;
    }
    if (!productDetailId) {
      setCartMsg({ type: 'error', text: '옵션을 선택하세요.' });
      return;
    }
    const qty = Number(quantity);
    if (!Number.isInteger(qty) || qty < 1) {
      setCartMsg({ type: 'error', text: '수량을 올바르게 입력하세요.' });
      return;
    }
    // 재고 초과 시 서버 호출 전에 막아 즉시 안내 (서버 프로시저 검증의 1차 가드)
    if (selectedDetail && qty > selectedDetail.stockQuantity) {
      setCartMsg({
        type: 'error',
        text: `재고가 부족합니다. (남은 재고 ${selectedDetail.stockQuantity}개)`,
      });
      return;
    }
    try {
      await cartApi.addItem(member.memberId, Number(productDetailId), qty);
      setCartMsg({ type: 'success', text: '장바구니에 담았습니다.' });
    } catch (e) {
      setCartMsg({ type: 'error', text: e.message });
    }
  };

  if (loading) return <div className="page"><Loading /></div>;
  if (error) return <div className="page"><ErrorBox message={error} /></div>;
  if (!product) return null;

  return (
    <div className="page">
      <Link to="/" className="back-link">← 상품 목록</Link>

      <div className="detail-top">
        <ProductThumb name={product.productName} className="thumb-lg" />

        <div className="detail-info">
          <span className="product-brand">{product.manufacturer}</span>
          <h1>{product.productName}</h1>
          <span className="product-category">{product.category}</span>
          <div className="detail-price">{formatPrice(product.price)}</div>

          <div className="cart-box">
            <p className="hint">옵션을 선택하세요</p>

            {optionGroups.length > 0 ? (
              <div className="option-groups">
                {optionGroups.map((g) => (
                  <div key={g.optionTypeName} className="option-group">
                    <span className="option-label">{g.optionTypeName}</span>
                    <div className="option-values">
                      {g.values.map((v) => {
                        const active =
                          String(selectedOptions[g.optionTypeName]) === String(v.optionDetailId);
                        const available = isValueAvailable(g.optionTypeName, v.optionDetailId);
                        return (
                          <button
                            key={v.optionDetailId}
                            type="button"
                            disabled={!available && !active}
                            className={
                              'chip option-chip' +
                              (active ? ' active' : '') +
                              (!available ? ' soldout' : '')
                            }
                            onClick={() => pickOption(g.optionTypeName, v.optionDetailId)}
                          >
                            {v.optionDetailName}
                          </button>
                        );
                      })}
                    </div>
                  </div>
                ))}
              </div>
            ) : details.length === 0 ? (
              <p className="hint">등록된 옵션(SKU)이 없습니다.</p>
            ) : (
              // 옵션 그룹 정보가 없을 때의 폴백: SKU 직접 선택
              <div className="sku-list">
                {details.map((d) => {
                  const soldOut = d.stockQuantity <= 0;
                  return (
                    <button
                      key={d.productDetailId}
                      type="button"
                      disabled={soldOut}
                      className={
                        'sku-option' +
                        (String(productDetailId) === String(d.productDetailId) ? ' active' : '') +
                        (soldOut ? ' soldout' : '')
                      }
                      onClick={() =>
                        setProductDetailId((prev) =>
                          String(prev) === String(d.productDetailId)
                            ? ''
                            : String(d.productDetailId)
                        )
                      }
                    >
                      <span className="sku-label">
                        {d.options?.map((o) => o.optionDetailName).join(' / ') ||
                          `상품상세 #${d.productDetailId}`}
                      </span>
                      <span className="sku-meta">
                        {d.surcharge > 0 ? `+${formatPrice(d.surcharge)} · ` : ''}
                        {soldOut ? '품절' : `재고 ${d.stockQuantity}`}
                      </span>
                    </button>
                  );
                })}
              </div>
            )}

            {optionGroups.length > 0 &&
              optionGroups.every((g) => selectedOptions[g.optionTypeName]) &&
              !selectedDetail && (
                <p className="msg-error">선택하신 옵션 조합은 현재 구매할 수 없습니다.</p>
              )}

            <div className="cart-row">
              <input
                type="number"
                min="1"
                value={quantity}
                onChange={(e) => setQuantity(e.target.value)}
                aria-label="수량"
              />
              <button className="btn-primary" onClick={addToCart} disabled={!productDetailId}>
                장바구니 담기
              </button>
            </div>

            {selectedDetail && (
              <p className="detail-sku-info">
                선택: {selectedDetail.options?.map((o) => o.optionDetailName).join(' / ') ||
                  `SKU #${selectedDetail.productDetailId}`}{' '}
                · 재고 {selectedDetail.stockQuantity} · 최종가{' '}
                {formatPrice((product.price || 0) + (selectedDetail.surcharge || 0))}
              </p>
            )}
            {cartMsg && (
              <p className={cartMsg.type === 'error' ? 'msg-error' : 'msg-success'}>
                {cartMsg.text}
              </p>
            )}
          </div>
        </div>
      </div>

      <ReviewSection
        productId={productId}
        reviews={reviews}
        details={details}
        member={member}
        onChanged={loadReviews}
        defaultProductDetailId={productDetailId}
      />
    </div>
  );
}

function skuLabel(detail) {
  return (
    detail.options?.map((o) => o.optionDetailName).join(' / ') ||
    `상품상세 #${detail.productDetailId}`
  );
}

function ReviewSection({ productId, reviews, details, member, onChanged, defaultProductDetailId }) {
  const [rating, setRating] = useState(5);
  const [content, setContent] = useState('');
  const [pdId, setPdId] = useState('');
  const [msg, setMsg] = useState(null);

  // 위에서 옵션을 고르면 그 SKU로, 아니면 첫 번째 옵션으로 기본 선택
  useEffect(() => {
    if (defaultProductDetailId) {
      setPdId(String(defaultProductDetailId));
    } else if (details.length > 0) {
      setPdId((prev) => prev || String(details[0].productDetailId));
    }
  }, [defaultProductDetailId, details]);

  const submit = async (e) => {
    e.preventDefault();
    setMsg(null);
    if (!member) return setMsg({ type: 'error', text: '로그인이 필요합니다.' });
    if (!pdId) return setMsg({ type: 'error', text: '리뷰를 남길 옵션을 선택하세요.' });
    try {
      await reviewApi.create(Number(pdId), member.memberId, { rating: Number(rating), content });
      setContent('');
      setMsg({ type: 'success', text: '리뷰가 등록되었습니다.' });
      onChanged();
    } catch (err) {
      setMsg({ type: 'error', text: err.message });
    }
  };

  const remove = async (reviewId) => {
    if (!member) return;
    try {
      await reviewApi.remove(reviewId, member.memberId);
      onChanged();
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <section className="review-section">
      <h2>리뷰 ({reviews.length})</h2>

      <form className="review-form" onSubmit={submit}>
        <div className="review-form-row">
          <label>
            평점
            <select value={rating} onChange={(e) => setRating(e.target.value)}>
              {[5, 4, 3, 2, 1].map((r) => (
                <option key={r} value={r}>{r}점</option>
              ))}
            </select>
          </label>
          <label>
            옵션 선택
            {details.length > 0 ? (
              <select value={pdId} onChange={(e) => setPdId(e.target.value)}>
                {details.map((d) => (
                  <option key={d.productDetailId} value={d.productDetailId}>
                    {skuLabel(d)}
                  </option>
                ))}
              </select>
            ) : (
              <input value="" placeholder="옵션 없음" disabled />
            )}
          </label>
        </div>
        <textarea
          placeholder="리뷰를 작성해 주세요."
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        <button className="btn-primary" type="submit">리뷰 등록</button>
        {msg && (
          <p className={msg.type === 'error' ? 'msg-error' : 'msg-success'}>{msg.text}</p>
        )}
      </form>

      {reviews.length === 0 ? (
        <Empty message="아직 등록된 리뷰가 없습니다." />
      ) : (
        <ul className="review-list">
          {reviews.map((r) => {
            const d = details.find(
              (x) => String(x.productDetailId) === String(r.productDetailId)
            );
            return (
            <li key={r.reviewId} className="review-item">
              <div className="review-head">
                <Stars rating={r.rating} />
                <span className="review-meta">
                  회원 #{r.memberId} · {d ? skuLabel(d) : `SKU #${r.productDetailId}`}
                </span>
                {member && member.memberId === r.memberId && (
                  <button className="btn-text danger" onClick={() => remove(r.reviewId)}>
                    삭제
                  </button>
                )}
              </div>
              <p className="review-content">{r.content}</p>
            </li>
            );
          })}
        </ul>
      )}
    </section>
  );
}
