# E-Commerce Frontend

Spring Boot 백엔드(`/backend`)의 REST API에 맞춰 동작하는 React(Vite) 쇼핑몰 프론트엔드입니다.

## 실행 방법

```bash
# 0) (최초 1회) 장바구니/주문 저장 프로시저·함수 설치
#    cart/order 기능은 add_cart / get_cart_price / CheckoutPartialCart 에 의존합니다.
cd ../backend && mysql -uroot ecommerce < src/main/resources/procedures.sql

# 1) 백엔드 실행 (기본 포트 8080)
./gradlew bootRun

# 2) 프론트엔드 실행
cd ../frontend
npm install
npm run dev      # http://localhost:5173
```

> `schema.sql` 은 부팅 때마다 테이블을 DROP/CREATE 하므로 데이터는 초기화되지만,
> 프로시저/함수는 테이블 DROP 으로 사라지지 않으므로 한 번만 설치하면 됩니다.

개발 서버(Vite)가 백엔드 경로(`/products`, `/members`, `/carts`, `/orders`,
`/product-details`, `/product`, `/reviews`, `/admin`)를 `http://localhost:8080`으로
프록시하므로 별도의 CORS 설정 없이 동작합니다. 백엔드 포트가 다르면
`vite.config.js`의 `backendTarget`을 수정하세요.

## 주요 화면 / 매핑된 API

| 화면 | 경로 | 사용 API |
|------|------|----------|
| 상품 목록/필터/정렬 | `/` | `GET /products`, `GET /products/category/{id}` |
| 상품 상세 + 옵션 + 리뷰 | `/products/:id` | `GET /products/{id}`, `GET /products/{id}/options`, `GET /product/{id}/reviews`, `GET /product-details/{id}` |
| 장바구니 | `/cart` | `GET/POST/PATCH/DELETE /carts/{memberId}/items` |
| 주문/결제 | `/checkout` | `POST /orders` |
| 주문내역 / 상세 / 취소 | `/orders`, `/orders/:id` | `GET /orders`, `GET /orders/{id}`, `PATCH /orders/{id}/cancel` |
| 로그인 / 회원가입 / 내정보 | `/login`, `/signup`, `/profile` | `GET/POST/PATCH/DELETE /members` |
| 판매 통계(관리자) | `/admin/stats` | `GET /admin/stats/sales/products`, `/categories` |

## 인증 처리

백엔드에 로그인 엔드포인트가 없어, `GET /members/{memberId}`로 회원을 조회해
"로그인 상태"를 만들고 `localStorage`에 보관합니다. 시드 데이터 기준 회원 ID는 1~5입니다.

## 배포 (백엔드 AWS + 프론트 Vercel, CORS 방식)

프론트(Vercel)와 백엔드(AWS)가 서로 다른 출처이므로, 프론트는 백엔드 풀 주소로 직접 호출하고
백엔드는 CORS 로 그 출처를 허용한다. (백엔드는 HTTPS 필요 — Vercel 이 HTTPS 라 HTTP 백엔드 호출은 차단됨)

**백엔드 (AWS) 환경변수**
```bash
SPRING_DATASOURCE_URL=jdbc:mysql://<RDS>:3306/ecommerce?serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
SPRING_SQL_INIT_MODE=never                 # 최초 schema/data/procedures 적재 후 never
APP_CORS_ALLOWED_ORIGINS=https://내앱.vercel.app,https://*.vercel.app   # 프론트 출처 허용
```
- DB 에 `schema.sql` → `data.sql` → `procedures.sql` 을 1회 적용 (procedures 누락 시 장바구니/주문 500)
- CORS 설정: `backend/.../global/config/CorsConfig.java`

**프론트 (Vercel) 환경변수**
```bash
VITE_API_BASE_URL=https://api.내도메인.com   # AWS 백엔드의 HTTPS 주소
```
- 개발에서는 이 값을 비워둔다 → Vite 프록시가 백엔드로 전달(코드 변경 없이 dev/prod 동시 지원)
- Vite 환경변수는 빌드 타임 주입 → 값 변경 시 재배포 필요
- Vercel Import 시 **Root Directory = `frontend`**, Framework = Vite

## 백엔드 컨트롤러 매핑 수정 내역

프론트엔드가 정상 동작하도록 아래 컨트롤러 매핑을 수정했습니다(`/backend`).

- **상품상세 생성/목록** `ProductDetailController`: 경로에 없는 `@PathVariable("productId")`를
  `@RequestParam("productId")`로 변경. 이제 `GET /product-details?productId=`로 상품의 SKU 목록을
  조회할 수 있어, 상품 상세 화면에서 옵션(SKU)을 **선택**해 장바구니/리뷰에 담습니다.
  (`PATCH "{productDetailId}"` → `"/{productDetailId}"`로 경로도 정리)
- **상품 검색** `ProductController#searchProduct`: `@RequestBody` → `@RequestParam keyword`로 변경하여
  `GET /products/search?keyword=`가 브라우저에서 동작합니다.
- **기간별 매출** `StatisticController#getSalesStats`: `@RequestBody` → `@RequestParam(period/startDate/endDate)`로
  변경하여 `GET /admin/stats/sales?period=&startDate=&endDate=`가 동작합니다. 통계 화면에 기간별 매출 차트를 추가했습니다.
