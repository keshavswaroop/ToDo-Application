import axios from "axios";

const AUTH_URL = "http://localhost:8080/api/auth";

export function registerApi(data) {
  return axios.post(AUTH_URL + "/register", data);
}

export function loginApi(data) {
  return axios.post(AUTH_URL + "/login", data);
}
//note: for the setItem, we donot require return keyword

//store token in the localstorage
export function storeToken(token) {
  localStorage.setItem("token", token); //here setItem("token", token), the 1st argument is th key, the arguments are in the key value pair.
}

//fetching the token
export function getToken() {
  return localStorage.getItem("token");
}

export function saveLoggedInUser(username, role) {
  //here we are using session storage because the data is tempraroly stored in the sessionstorage until the browser is closed whereas in localstorage, the data is stores for a for a long time.
  sessionStorage.setItem("AuthenticatedUser", username);
  sessionStorage.setItem("role", role);
}

//this is to check is the user has logged in or not
export function isUserLoggedIn() {
  //getting the username from the sessionstorage
  const username = sessionStorage.getItem("AuthenticatedUser");

  if (username == null) {
    return false;
  } else {
    return true;
  }
}

//getting the username of the logged in user.
export function getLoggedInUser() {
  const username = sessionStorage.getItem("AuthenticatedUser");
  return username;
}

export function logout() {
  //clear the local and session storage

  localStorage.clear();
  sessionStorage.clear();
}

export function isAdminUser() {
  let role = sessionStorage.getItem("role");

  if (role != null && role === "ROLE_ADMIN") {
    return true;
  } else {
    return false;
  }
}
