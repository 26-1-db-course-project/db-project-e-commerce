import { createContext, useContext, useEffect, useState, useCallback } from 'react';
import { memberApi } from '../api';

// 백엔드에 별도 인증/로그인 엔드포인트가 없으므로,
// "현재 로그인한 회원"을 memberId 기반으로 localStorage 에 보관한다.
const MemberContext = createContext(null);

const STORAGE_KEY = 'ecommerce.currentMember';

export function MemberProvider({ children }) {
  const [member, setMember] = useState(() => {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  });

  useEffect(() => {
    if (member) localStorage.setItem(STORAGE_KEY, JSON.stringify(member));
    else localStorage.removeItem(STORAGE_KEY);
  }, [member]);

  // memberId 로 회원 정보를 조회해 "로그인" 처리
  const login = useCallback(async (memberId) => {
    const data = await memberApi.get(memberId);
    setMember({ memberId: data.memberId, loginId: data.loginId });
    return data;
  }, []);

  const logout = useCallback(() => setMember(null), []);

  return (
    <MemberContext.Provider value={{ member, setMember, login, logout }}>
      {children}
    </MemberContext.Provider>
  );
}

export function useMember() {
  const ctx = useContext(MemberContext);
  if (!ctx) throw new Error('useMember must be used within MemberProvider');
  return ctx;
}