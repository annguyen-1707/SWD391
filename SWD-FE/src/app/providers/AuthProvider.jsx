import React, {
    createContext,
    useCallback,
    useEffect,
    useMemo,
    useState,
} from "react";
import { authService } from "../services/AuthService";

import { getUserInfo } from '@/features/admin/users/services/UserService';
export const AuthDataContext = createContext();
export const AuthActionContext = createContext();

const AuthProvider = ({ children }) => {
    const [user, setUser] = useState({});
    const [theme, setTheme] = useState("Light");
    const [language, setLanguage] = useState("EN")

    useEffect(() => {
        const storedUser = localStorage.getItem("userInfo");
        const storedTheme = localStorage.getItem("theme");
        const storedLang = localStorage.getItem("language");
        console.log("AuthProvider - Stored User:", storedUser);
        console.log("AuthProvider - Stored Theme:", storedTheme);
        console.log("AuthProvider - Stored Language:", storedLang);

        if (storedUser) {
            try {
                setUser(JSON.parse(storedUser));
            } catch {
                localStorage.removeItem("userInfo");
                setUser({});
            }
        }
        if (storedTheme) setTheme(storedTheme);
        if (storedLang) setLanguage(storedLang);

    }, []);


    const checkAuth = useCallback(async () => {
        try {
            const res = await getUserInfo();
            localStorage.setItem("userInfo", JSON.stringify(res.data));
            console.log("Authentication check successful:", res);
            const newUser = res.data.data;
            localStorage.setItem("userInfo", JSON.stringify(newUser));

            setUser(newUser);
        } catch (error) {
            setUser(null);
            console.error("Authentication check failed:", error);
            throw error;
        }
    }, []);

    // 🔐 LOGIN
    const login = useCallback(async (payload) => {
        try {
            const data = await authService.login(payload);
            console.log("Login successful:", data);
            const accessToken =
                data?.data ??
                data?.accessToken ??
                data?.access_token ??
                data?.token ??
                data?.jwt;

            if (accessToken) {
                localStorage.setItem("access_token", accessToken);
            }

            if (data?.user) {
                setUser(data.user);
                localStorage.setItem("userInfo", JSON.stringify(data.user));
            }

            await checkAuth();
        } catch (error) {
            throw error;
        }
    }, [checkAuth]);

    // 🚪 LOGOUT
    const logout = useCallback(() => {
        localStorage.removeItem("access_token");
        localStorage.removeItem("userInfo");
        authService.logout();
        setUser({});
    }, []);

    // 👤 CHANGE USER
    const changeUser = useCallback((newUser) => {
        setUser(newUser);
        localStorage.setItem("userInfo", JSON.stringify(newUser));
    }, []);

    // 🎨 CHANGE THEME
    const changeTheme = useCallback((newTheme) => {
        setTheme(newTheme);
        localStorage.setItem("theme", newTheme);
    }, []);

    // 🌍 CHANGE LANGUAGE
    const changeLanguage = useCallback((newLang) => {
        setLanguage(newLang);
        localStorage.setItem("language", newLang);
    }, []);

    const stateValues = useMemo(() => ({ user, language, theme }), [user, language, theme]);
    const actionValues = useMemo(
        () => ({
            login,
            logout,
            changeUser,
            changeTheme,
            changeLanguage,
            checkAuth,
        }),
        [login, logout, changeUser, changeTheme, changeLanguage, checkAuth]
    );

    return (
        <>
            <AuthDataContext.Provider value={stateValues}>
                <AuthActionContext.Provider value={actionValues}>
                    {children}
                </AuthActionContext.Provider>
            </AuthDataContext.Provider>
        </>
    )
}

export default AuthProvider
