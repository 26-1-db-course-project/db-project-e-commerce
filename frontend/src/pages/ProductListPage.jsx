import { useEffect, useMemo, useState } from 'react';
import { productApi } from '../api';
import { CATEGORIES, SORT_OPTIONS } from '../constants';
import ProductCard from '../components/ProductCard';
import { Loading, ErrorBox, Empty } from '../components/common';

export default function ProductListPage() {
  const [products, setProducts] = useState([]);
  const [count, setCount] = useState(0);
  const [categoryId, setCategoryId] = useState(null);
  const [sortBy, setSortBy] = useState('price,asc');
  const [keyword, setKeyword] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let active = true;
    setLoading(true);
    setError(null);

    const fetcher = categoryId
      ? productApi.listByCategory(categoryId, sortBy)
      : productApi.list(sortBy);

    fetcher
      .then((data) => {
        if (!active) return;
        setProducts(data.productResponseList || []);
        setCount(data.productCount || 0);
      })
      .catch((e) => active && setError(e.message))
      .finally(() => active && setLoading(false));

    return () => {
      active = false;
    };
  }, [categoryId, sortBy]);

  // 카테고리 필터와 함께 즉시 반응하도록 목록 결과를 클라이언트에서 키워드 필터링한다.
  // (서버 검색이 필요하면 productApi.search(keyword, sortBy) 사용 가능 — GET /products/search)
  const visible = useMemo(() => {
    const k = keyword.trim().toLowerCase();
    if (!k) return products;
    return products.filter(
      (p) =>
        p.productName?.toLowerCase().includes(k) ||
        p.manufacturer?.toLowerCase().includes(k) ||
        p.category?.toLowerCase().includes(k)
    );
  }, [products, keyword]);

  return (
    <div className="page">
      <section className="hero">
        <h1>이번 시즌 신상품</h1>
        <p>데이터베이스 프로젝트 · 쇼핑몰 데모 스토어</p>
      </section>

      <div className="toolbar">
        <div className="category-tabs">
          <button
            className={categoryId === null ? 'chip active' : 'chip'}
            onClick={() => setCategoryId(null)}
          >
            전체
          </button>
          {CATEGORIES.map((c) => (
            <button
              key={c.id}
              className={categoryId === c.id ? 'chip active' : 'chip'}
              onClick={() => setCategoryId(c.id)}
            >
              {c.name}
            </button>
          ))}
        </div>

        <div className="toolbar-right">
          <input
            className="search-input"
            placeholder="상품 검색"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
            {SORT_OPTIONS.map((o) => (
              <option key={o.value} value={o.value}>
                {o.label}
              </option>
            ))}
          </select>
        </div>
      </div>

      {loading ? (
        <Loading />
      ) : error ? (
        <ErrorBox message={error} />
      ) : visible.length === 0 ? (
        <Empty message="조건에 맞는 상품이 없습니다." />
      ) : (
        <>
          <p className="result-count">총 {count}개 상품</p>
          <div className="product-grid">
            {visible.map((p) => (
              <ProductCard key={p.productId} product={p} />
            ))}
          </div>
        </>
      )}
    </div>
  );
}