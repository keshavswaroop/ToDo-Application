import { NavLink, useNavigate } from "react-router-dom";
import { isUserLoggedIn, logout } from "../services/AuthService";

const Header = () => {
  const isAuth = isUserLoggedIn();
  const navigate = useNavigate();
  function handleLogout() {
    logout();
    navigate("/login");
  }
  return (
    <>
      <nav className="navbar navbar-expand-lg navbar navbar-dark bg-dark">
        <div>
          <a className="navbar-brand" href="#">
            Todo Management Application
          </a>
        </div>
        <div className="collapse navbar-collapse">
          <ul className="navbar-nav">
            {isAuth && (
              <li className="nav-item">
                <NavLink to="/todos" className="nav-link">
                  Todo
                </NavLink>
              </li>
            )}
          </ul>
        </div>
        <ul className="navbar-nav">
          {!isAuth && (
            <li className="nav-item">
              <NavLink to="/register" className="nav-link">
                Register
              </NavLink>
            </li>
          )}

          {!isAuth && (
            <li className="nav-item">
              <NavLink to="/login" className="nav-link">
                Login
              </NavLink>
            </li>
          )}

          {isAuth && (
            <li className="nav-item">
              <NavLink to="/login" className="nav-link" onClick={handleLogout}>
                Logout
              </NavLink>
            </li>
          )}
        </ul>
      </nav>
    </>
  );
};

export default Header;
