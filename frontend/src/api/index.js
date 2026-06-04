import { api } from './client';

// ===== 회원 (MemberController: /members) =====
export const memberApi = {
  // POST /members
  create: (request) => api.post('/members', { body: request }),
  // GET /members/{memberId}
  get: (memberId) => api.get(`/members/${memberId}`),
  // PATCH /members/{memberId}/{addressId}  (주소 수정)
  updateAddress: (memberId, addressId, request) =>
    api.patch(`/members/${memberId}/${addressId}`, { body: request }),
  // POST /members/{memberId}/addresses  (주소 추가)
  addAddress: (memberId, request) =>
    api.post(`/members/${memberId}/addresses`, { body: request }),
  // DELETE /members/{memberId}
  remove: (memberId) => api.del(`/members/${memberId}`),
};

// ===== 상품 (ProductController: /products) =====
export const productApi = {
  // GET /products?sortBy=
  list: (sortBy = 'productName,desc') => api.get('/products', { query: { sortBy } }),
  // GET /products/category/{categoryId}?sortBy=
  listByCategory: (categoryId, sortBy = 'productName,desc') =>
    api.get(`/products/category/${categoryId}`, { query: { sortBy } }),
  // GET /products/{productId}
  get: (productId) => api.get(`/products/${productId}`),
  // GET /products/{productId}/options
  options: (productId) => api.get(`/products/${productId}/options`),
  // GET /products/search?keyword=&sortBy=
  search: (keyword, sortBy = 'productName,desc') =>
    api.get('/products/search', { query: { keyword, sortBy } }),
  // POST /products
  create: (request) => api.post('/products', { body: request }),
  // PATCH /products/{productId}  (가격 수정)
  updatePrice: (productId, price) =>
    api.patch(`/products/${productId}`, { body: { price } }),
  // DELETE /products/{productId}
  remove: (productId) => api.del(`/products/${productId}`),
};

// ===== 상품 상세/SKU (ProductDetailController: /product-details) =====
export const productDetailApi = {
  // GET /product-details?productId=  (특정 상품의 SKU 목록)
  listByProduct: (productId) => api.get('/product-details', { query: { productId } }),
  // GET /product-details/{productDetailId}
  get: (productDetailId) => api.get(`/product-details/${productDetailId}`),
  // POST /product-details?productId=
  create: (productId, request) =>
    api.post('/product-details', { query: { productId }, body: request }),
  // PATCH /product-details/{productDetailId}
  update: (productDetailId, request) =>
    api.patch(`/product-details/${productDetailId}`, { body: request }),
};

// ===== 장바구니 (CartController: /carts/{memberId}) =====
export const cartApi = {
  // GET /carts/{memberId}
  get: (memberId) => api.get(`/carts/${memberId}`),
  // POST /carts/{memberId}/items
  addItem: (memberId, productDetailId, quantity) =>
    api.post(`/carts/${memberId}/items`, { body: { productDetailId, quantity } }),
  // PATCH /carts/{memberId}/items/{productDetailId}
  updateQuantity: (memberId, productDetailId, quantity) =>
    api.patch(`/carts/${memberId}/items/${productDetailId}`, { body: { quantity } }),
  // DELETE /carts/{memberId}/items/{productDetailId}
  removeItem: (memberId, productDetailId) =>
    api.del(`/carts/${memberId}/items/${productDetailId}`),
};

// ===== 주문 (OrderController: /orders) =====
export const orderApi = {
  // POST /orders  -> 평문 결과 메시지 반환
  create: ({ memberId, deliveryAddressId, selectedProductDetailIds }) =>
    api.post('/orders', {
      body: { memberId, deliveryAddressId, selectedProductDetailIds },
    }),
  // GET /orders?memberId=&startDate=
  list: (memberId, startDate) =>
    api.get('/orders', { query: { memberId, startDate } }),
  // GET /orders/{orderId}?memberId=
  get: (orderId, memberId) => api.get(`/orders/${orderId}`, { query: { memberId } }),
  // PATCH /orders/{orderId}/cancel?memberId=
  cancel: (orderId, memberId) =>
    api.patch(`/orders/${orderId}/cancel`, { query: { memberId } }),
};

// ===== 리뷰 (ReviewController) =====
export const reviewApi = {
  // GET /product/{productId}/reviews
  listByProduct: (productId) => api.get(`/product/${productId}/reviews`),
  // POST /product-details/{productDetailId}/reviews?auth-id=
  create: (productDetailId, memberId, { rating, content }) =>
    api.post(`/product-details/${productDetailId}/reviews`, {
      query: { 'auth-id': memberId },
      body: { rating, content },
    }),
  // PATCH /reviews/{reviewId}?auth-id=
  update: (reviewId, memberId, { rating, content }) =>
    api.patch(`/reviews/${reviewId}`, {
      query: { 'auth-id': memberId },
      body: { rating, content },
    }),
  // DELETE /reviews/{reviewId}?auth-id=
  remove: (reviewId, memberId) =>
    api.del(`/reviews/${reviewId}`, { query: { 'auth-id': memberId } }),
};

// ===== 통계 (StatisticController: /admin/stats/sales) =====
export const statisticApi = {
  // GET /admin/stats/sales?period=&startDate=&endDate=
  periodSales: ({ period = 'MONTHLY', startDate, endDate } = {}) =>
    api.get('/admin/stats/sales', { query: { period, startDate, endDate } }),
  // GET /admin/stats/sales/products
  productSales: () => api.get('/admin/stats/sales/products'),
  // GET /admin/stats/sales/categories
  categorySales: () => api.get('/admin/stats/sales/categories'),
};