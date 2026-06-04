// 백엔드 컨트롤러와 통신하는 공용 HTTP 클라이언트.
//
// API_BASE 동작:
//  - 개발: VITE_API_BASE_URL 미설정 → '' → '/products' 처럼 호출 → Vite 프록시가 백엔드로 전달
//  - 운영(Vercel): VITE_API_BASE_URL = https://api.내도메인.com 으로 설정
//                  → 백엔드 풀 주소로 직접 호출(백엔드 CORS 가 허용). 예) https://api.내도메인.com/products
const API_BASE = import.meta.env.VITE_API_BASE_URL ?? '';

export class ApiError extends Error {
  constructor(message, status, body) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
    this.body = body;
  }
}

async function request(method, path, { body, query } = {}) {
  let url = API_BASE + path;
  if (query) {
    const params = new URLSearchParams();
    Object.entries(query).forEach(([k, v]) => {
      if (v !== undefined && v !== null && v !== '') params.append(k, v);
    });
    const qs = params.toString();
    if (qs) url += `?${qs}`;
  }

  const options = { method, headers: {} };
  if (body !== undefined) {
    options.headers['Content-Type'] = 'application/json';
    options.body = JSON.stringify(body);
  }

  const res = await fetch(url, options);

  // 본문이 없는 응답(204, 201 빈 바디 등) 처리
  const text = await res.text();
  let data = null;
  if (text) {
    try {
      data = JSON.parse(text);
    } catch {
      data = text; // 평문 응답(주문 생성 결과 메시지 등)
    }
  }

  if (!res.ok) {
    const message =
      (data && (data.message || data.error)) ||
      (typeof data === 'string' && data) ||
      `요청 실패 (${res.status})`;
    throw new ApiError(message, res.status, data);
  }

  return data;
}

export const api = {
  get: (path, opts) => request('GET', path, opts),
  post: (path, opts) => request('POST', path, opts),
  patch: (path, opts) => request('PATCH', path, opts),
  put: (path, opts) => request('PUT', path, opts),
  del: (path, opts) => request('DELETE', path, opts),
};