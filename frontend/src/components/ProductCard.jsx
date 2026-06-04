import { Link } from 'react-router-dom';
import { formatPrice } from '../constants';
import { ProductThumb } from './common';

export default function ProductCard({ product }) {
  return (
    <Link to={`/products/${product.productId}`} className="product-card">
      <ProductThumb name={product.productName} />
      <div className="product-card-body">
        <span className="product-brand">{product.manufacturer}</span>
        <span className="product-name">{product.productName}</span>
        <span className="product-category">{product.category}</span>
        <span className="product-price">{formatPrice(product.price)}</span>
      </div>
    </Link>
  );
}