import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// 백엔드(Spring Boot, 기본 8080)는 별도의 CORS 설정이 없으므로
// 개발 서버에서 백엔드 경로들을 프록시하여 동일 출처처럼 호출한다.
const backendTarget = 'http://localhost:8080';
const backendPaths = [
  '/members',
  '/products',
  '/product-details',
  '/product',
  '/carts',
  '/orders',
  '/reviews',
  '/admin',
];

export default defineConfig({
  plugins: [react()],
  server: {
    // host: true → 0.0.0.0 바인딩. 같은 네트워크(LAN)의 다른 기기에서 접속 가능.
    host: true,
    port: 5173,
    proxy: Object.fromEntries(
      backendPaths.map((p) => [p, { target: backendTarget, changeOrigin: true }])
    ),
  },
});
