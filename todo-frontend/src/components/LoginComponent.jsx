import { useState } from "react";
import {
  loginApi,
  saveLoggedInUser,
  storeToken,
} from "../services/AuthService";
import { useNavigate } from "react-router-dom";

const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function handleLogin(e) {
    e.preventDefault();
    const log = { usernameOrEmail: username, password };
    console.log(log);
    await loginApi(log) //async and await is because we want the save username funciton to be called once we submit the form and not when we load the form.
      .then((response) => {
        console.log(response.data);

        //creating a basic auth token and storing it in the localstorage
        //const token = "Basic " + window.btoa(username + ":" + password);

        //creating jwt token
        const token = "Bearer " + response.data.accessToken;

        //get role from token
        const role = response.data.role;
        storeToken(token);

        saveLoggedInUser(username, role);

        navigate("/todos");

        //we need to refersh the page
        window.location.reload(false);
      })
      .catch((error) => console.error(error));
  }
  return (
    <div className="container">
      <br />
      <br />
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card">
            <div className="card-header">
              <h2 className="text-center">User Login form</h2>
            </div>
            <div className="card-body">
              <form>
                <div className="row mb-3">
                  <label className="col-md-3 control-label">
                    Username or Email
                  </label>
                  <div className="col-md-9">
                    <input
                      type="text"
                      name="username"
                      className="form-control"
                      placeholder="Enter username"
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                    ></input>
                  </div>
                </div>

                <div className="row mb-3">
                  <label className="col-md-3 control-label"> Password </label>
                  <div className="col-md-9">
                    <input
                      type="password"
                      name="password"
                      className="form-control"
                      placeholder="Enter password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    ></input>
                  </div>
                </div>

                <div className="form-group mb-3">
                  <button
                    className="btn btn-primary"
                    onClick={(e) => handleLogin(e)}
                  >
                    Login
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;
