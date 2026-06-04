import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useMember } from '../context/MemberContext';

export default function Navbar() {
  const { member, logout } = useMember();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="navbar">
      <div className="navbar-inner">
        <Link to="/" className="brand">
          SHOP<span>.</span>
        </Link>

        <nav className="nav-links">
          <NavLink to="/" end>상품</NavLink>
          <NavLink to="/cart">장바구니</NavLink>
          <NavLink to="/orders">주문내역</NavLink>
          <NavLink to="/admin/stats">통계</NavLink>
        </nav>

        <div className="nav-account">
          {member ? (
            <>
              <Link to="/profile" className="user-pill">
                {member.loginId} 님
              </Link>
              <button className="btn-text" onClick={handleLogout}>
                로그아웃
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="btn-text">로그인</Link>
              <Link to="/signup" className="btn-primary btn-sm">회원가입</Link>
            </>
          )}
        </div>
      </div>
    </header>
  );
}