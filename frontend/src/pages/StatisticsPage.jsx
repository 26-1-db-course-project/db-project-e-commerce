import { useEffect, useState } from 'react';
import { statisticApi } from '../api';
import { formatPrice } from '../constants';
import { Loading, ErrorBox, Empty } from '../components/common';

const PERIODS = [
  { value: 'DAILY', label: '일별' },
  { value: 'MONTHLY', label: '월별' },
  { value: 'YEARLY', label: '연별' },
];

export default function StatisticsPage() {
  const [productStats, setProductStats] = useState(null);
  const [categoryStats, setCategoryStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    Promise.all([statisticApi.productSales(), statisticApi.categorySales()])
      .then(([p, c]) => {
        setProductStats(p);
        setCategoryStats(c);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="page"><Loading /></div>;
  if (error) return <div className="page"><ErrorBox message={error} /></div>;

  const maxRevenue = Math.max(
    1,
    ...(categoryStats?.items || []).map((c) => Number(c.revenue) || 0)
  );

  return (
    <div className="page">
      <h1>판매 통계</h1>
      <p className="hint">관리자용 대시보드 · 기간/상품/카테고리 매출 집계</p>

      <PeriodSalesSection />

      <section className="stats-block">
        <div className="stats-head">
          <h2>카테고리별 매출</h2>
          <span className="stats-total">총 {formatPrice(categoryStats?.totalRevenue)}</span>
        </div>
        {(categoryStats?.items || []).length === 0 ? (
          <Empty message="집계된 매출이 없습니다." />
        ) : (
          <ul className="bar-list">
            {categoryStats.items.map((c) => (
              <li key={c.categoryName} className="bar-row">
                <span className="bar-label">{c.categoryName}</span>
                <div className="bar-track">
                  <div
                    className="bar-fill"
                    style={{ width: `${(Number(c.revenue) / maxRevenue) * 100}%` }}
                  />
                </div>
                <span className="bar-value">
                  {formatPrice(c.revenue)} · {Number(c.revenueRatio).toFixed(1)}%
                  <em> ({c.soldQuantity}개)</em>
                </span>
              </li>
            ))}
          </ul>
        )}
      </section>

      <section className="stats-block">
        <div className="stats-head">
          <h2>상품별 판매 순위</h2>
          <span className="stats-total">총 {productStats?.productTotal || 0}개 상품</span>
        </div>
        {(productStats?.items || []).length === 0 ? (
          <Empty message="집계된 상품 판매가 없습니다." />
        ) : (
          <table className="stats-table">
            <thead>
              <tr>
                <th>순위</th>
                <th>상품명</th>
                <th>카테고리</th>
                <th>판매수량</th>
                <th>매출</th>
              </tr>
            </thead>
            <tbody>
              {productStats.items.map((p) => (
                <tr key={p.productId}>
                  <td className="rank">{p.rank}</td>
                  <td>{p.productName}</td>
                  <td>{p.categoryName}</td>
                  <td>{p.soldQuantity}</td>
                  <td>{formatPrice(p.revenue)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </div>
  );
}

function PeriodSalesSection() {
  const [period, setPeriod] = useState('MONTHLY');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const load = () => {
    setLoading(true);
    setError(null);
    statisticApi
      .periodSales({ period, startDate: startDate || undefined, endDate: endDate || undefined })
      .then(setData)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const items = data?.items || [];
  const maxRevenue = Math.max(1, ...items.map((i) => Number(i.revenue) || 0));

  return (
    <section className="stats-block">
      <div className="stats-head">
        <h2>기간별 매출</h2>
        {data && (
          <span className="stats-total">
            총 {formatPrice(data.totalRevenue)} · {data.totalOrders || 0}건
          </span>
        )}
      </div>

      <div className="stats-controls">
        <select value={period} onChange={(e) => setPeriod(e.target.value)}>
          {PERIODS.map((p) => (
            <option key={p.value} value={p.value}>{p.label}</option>
          ))}
        </select>
        <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} />
        <span className="dash">~</span>
        <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
        <button className="btn-outline" onClick={load}>조회</button>
      </div>

      {loading ? (
        <Loading />
      ) : error ? (
        <ErrorBox message={error} />
      ) : items.length === 0 ? (
        <Empty message="해당 기간의 매출 데이터가 없습니다." />
      ) : (
        <ul className="bar-list">
          {items.map((it) => (
            <li key={it.label} className="bar-row">
              <span className="bar-label">{it.label}</span>
              <div className="bar-track">
                <div
                  className="bar-fill"
                  style={{ width: `${(Number(it.revenue) / maxRevenue) * 100}%` }}
                />
              </div>
              <span className="bar-value">
                {formatPrice(it.revenue)} <em>({it.orderCount}건)</em>
              </span>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}