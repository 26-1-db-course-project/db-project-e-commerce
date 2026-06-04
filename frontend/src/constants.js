// data.sql 시드 데이터 기준 카테고리 목록 (상품 목록 필터에 사용)
export const CATEGORIES = [
  { id: 1, name: '자켓' },
  { id: 2, name: '티셔츠' },
  { id: 3, name: '바지' },
  { id: 4, name: '스커트' },
  { id: 5, name: '아우터' },
  { id: 6, name: '니트' },
];

// ProductController 의 sortBy 파라미터 옵션
export const SORT_OPTIONS = [
  { value: 'price,asc', label: '가격 낮은순' },
  { value: 'price,desc', label: '가격 높은순' },
  { value: 'productName,asc', label: '이름 오름차순' },
  { value: 'productName,desc', label: '이름 내림차순' },
];

// CreateMemberRequest.role
export const MEMBER_ROLES = [
  { value: 'ROLE_CUSTOMER', label: '일반 고객' },
  { value: 'ROLE_BUSINESS', label: '판매 사업자' },
];

export function formatPrice(value) {
  if (value === null || value === undefined) return '-';
  return `${Number(value).toLocaleString('ko-KR')}원`;
}
