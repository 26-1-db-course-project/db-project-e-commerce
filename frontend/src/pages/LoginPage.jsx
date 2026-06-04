import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useMember } from '../context/MemberContext';

// 백엔드에 인증 API 가 없으므로 memberId 로 회원을 조회해 로그인 상태로 만든다.
export default function LoginPage() {
  const { login } = useMember();
  const navigate = useNavigate();
  const [memberId, setMemberId] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      const m = await login(Number(memberId));
      navigate('/');
      return m;
    } catch (err) {
      setError(err.status === 404 ? '존재하지 않는 회원입니다.' : err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page auth-page">
      <div className="auth-card">
        <h1>로그인</h1>
        <p className="hint">데모 환경입니다. 회원 ID 로 로그인하세요. (시드 데이터: 1 ~ 5)</p>
        <form onSubmit={submit}>
          <label className="field">
            회원 ID
            <input
              type="number"
              min="1"
              value={memberId}
              onChange={(e) => setMemberId(e.target.value)}
              placeholder="예: 2"
              required
            />
          </label>
          {error && <p className="msg-error">{error}</p>}
          <button className="btn-primary block" type="submit" disabled={loading}>
            {loading ? '확인 중...' : '로그인'}
          </button>
        </form>
        <p className="auth-foot">
          계정이 없으신가요? <Link to="/signup">회원가입</Link>
        </p>
      </div>
    </div>
  );
}
