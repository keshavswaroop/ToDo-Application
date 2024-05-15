import axios from "axios";
import { getToken } from "./AuthService";

const TODO_URL = "http://localhost:8080/api/todos";

// Add a request interceptor
axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    //here we are setting the token in the header
    config.headers["Authorization"] = getToken();
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

export const getAllTodos = () => {
  return axios.get(TODO_URL);
};

export const createTodo = (todo) => {
  return axios.post(TODO_URL, todo);
};

export const updateTodo = (id, todo) => {
  return axios.put(TODO_URL + "/" + id, todo);
};

export const fetchTodo = (id) => {
  return axios.get(TODO_URL + "/" + id);
};

export const deleteTodo = (id) => {
  return axios.delete(TODO_URL + "/" + id);
};

export const completeTodo = (id) => {
  return axios.patch(TODO_URL + "/" + id + "/complete");
};

export const incompleteTodo = (id) => {
  return axios.patch(TODO_URL + "/" + id + "/incomplete");
};
