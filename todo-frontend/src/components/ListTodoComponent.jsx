import { useEffect, useState } from "react";
import {
  completeTodo,
  deleteTodo,
  getAllTodos,
  incompleteTodo,
} from "../services/TodoService";
import { useNavigate } from "react-router-dom";
import { isAdminUser } from "../services/AuthService";

const isAdmin = isAdminUser();

const ListTodoComponent = () => {
  const [todo, setTodo] = useState([]);

  function getTodo() {
    getAllTodos()
      .then((response) => {
        setTodo(response.data);
      })
      .catch((error) => console.error(error));
  }

  useEffect(() => {
    getTodo();
  }, []);
  const navigate = useNavigate();
  function handleAdd() {
    navigate("/addTodo");
  }

  function handleUpdate(id) {
    navigate(`/updateTodo/${id}`);
  }

  function handleDelete(id) {
    deleteTodo(id)
      .then((response) => {
        console.log(response);
        getTodo();
      })
      .catch((error) => console.error(error));
  }

  function handleComplete(id) {
    completeTodo(id)
      .then((response) => {
        console.log(response.data);
        getTodo();
      })
      .catch((error) => console.error(error));
  }

  function handleInComplete(id) {
    incompleteTodo(id)
      .then((response) => {
        console.log(response.data);
        getTodo();
      })
      .catch((error) => console.error(error));
  }
  return (
    <div className="container">
      <h2 className="text-center">ToDo</h2>
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Todo Title</th>
            <th>Todo Description</th>
            <th>Todo Completed</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {todo.map((todos) => (
            <tr key={todos.id}>
              <td>{todos.title}</td>
              <td>{todos.description}</td>
              <td>{todos.completed ? "Yes" : "No"}</td>
              <td>
                {isAdmin && (
                  <button
                    className="btn btn-success"
                    onClick={() => handleUpdate(todos.id)}
                  >
                    Update
                  </button>
                )}
                {isAdmin && (
                  <button
                    className="btn btn-danger"
                    style={{ marginLeft: "10px" }}
                    onClick={() => handleDelete(todos.id)}
                  >
                    Delete
                  </button>
                )}

                <button
                  className="btn btn-info"
                  style={{ marginLeft: "10px" }}
                  onClick={() => handleComplete(todos.id)}
                >
                  Mark as Completed
                </button>
                <button
                  className="btn btn-warning"
                  style={{ marginLeft: "10px" }}
                  onClick={() => handleInComplete(todos.id)}
                >
                  Mark as Incomplete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isAdmin && (
        <button className="btn btn-primary" onClick={handleAdd}>
          Add Todo
        </button>
      )}
    </div>
  );
};

export default ListTodoComponent;
