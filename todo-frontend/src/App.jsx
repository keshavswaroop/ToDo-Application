import "./App.css";
import Footer from "./components/Footer";
import Header from "./components/Header";
import ListTodoComponent from "./components/ListTodoComponent";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import TodoComponent from "./components/TodoComponent";
import RegisterComponent from "./components/RegisterComponent";
import LoginComponent from "./components/LoginComponent";
import { isUserLoggedIn } from "./services/AuthService";

function App() {
  // eslint-disable-next-line react/prop-types
  function AuthenticatedRoutes({ children }) {
    const isAuth = isUserLoggedIn();

    if (isAuth) {
      return children;
    }
    return <Navigate to="/" />;
  }

  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          {/* <Route path="/" element={<ListTodoComponent />}></Route> */}
          <Route path="/" element={<LoginComponent />}></Route>

          <Route
            path="/todos"
            element={
              <AuthenticatedRoutes>
                <ListTodoComponent />
              </AuthenticatedRoutes>
            }
          ></Route>
          <Route
            path="/addTodo"
            element={
              <AuthenticatedRoutes>
                <TodoComponent />
              </AuthenticatedRoutes>
            }
          ></Route>
          <Route
            path="/updateTodo/:id"
            element={
              <AuthenticatedRoutes>
                <TodoComponent />
              </AuthenticatedRoutes>
            }
          ></Route>
          <Route
            path="/:id/complete"
            element={
              <AuthenticatedRoutes>
                <ListTodoComponent />
              </AuthenticatedRoutes>
            }
          ></Route>
          <Route path="/register" element={<RegisterComponent />}></Route>
          <Route path="/login" element={<LoginComponent />}></Route>
        </Routes>
        <Footer />
      </BrowserRouter>
    </>
  );
}

export default App;
