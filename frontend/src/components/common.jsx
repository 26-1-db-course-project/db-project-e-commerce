// 작은 공용 UI 헬퍼 컴포넌트 모음

export function Loading({ label = '불러오는 중...' }) {
  return <div className="state-box">{label}</div>;
}

export function ErrorBox({ message }) {
  return <div className="state-box state-error">⚠ {message}</div>;
}

export function Empty({ message = '데이터가 없습니다.' }) {
  return <div className="state-box">{message}</div>;
}

export function Stars({ rating }) {
  const full = Math.max(0, Math.min(5, rating));
  return (
    <span className="stars" title={`${rating}점`}>
      {'★'.repeat(full)}
      {'☆'.repeat(5 - full)}
    </span>
  );
}

// 시드 상품 image_url 들은 실제 이미지가 아니므로(http://img/...) placeholder 로 대체
export function ProductThumb({ name, className = '' }) {
  const initial = (name || '?').trim().charAt(0);
  return <div className={`thumb ${className}`}>{initial}</div>;
}