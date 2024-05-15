import { useEffect, useState } from "react";
import { createTodo, fetchTodo, updateTodo } from "../services/TodoService";
import { useNavigate, useParams } from "react-router-dom";

const TodoComponent = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [completed, setCompleted] = useState(false);

  const [errors, setErrors] = useState({
    title: "",
    description: "",
    completed: "",
  });

  function validateInputs() {
    let valid = true;
    const copyError = { ...errors };

    if (title.trim()) {
      copyError.title = "";
    } else {
      copyError.title = "Title cannot be empty";
      valid = false;
    }

    if (description.trim()) {
      copyError.description = "";
    } else {
      copyError.description = "Description cannot be empty";
      valid = false;
    }

    setErrors(copyError);

    return valid;
  }

  function handleTitle(e) {
    setTitle(e.target.value);
  }

  function handleDescription(e) {
    setDescription(e.target.value);
  }

  const navigate = useNavigate();

  function handleAdd(e) {
    e.preventDefault();

    if (validateInputs()) {
      const todo = { title, description, completed };
      if (id) {
        updateTodo(id, todo)
          .then((response) => {
            console.log(response.data);
            navigate("/todos");
          })
          .catch((error) => console.error(error));
      } else {
        createTodo(todo)
          .then((response) => {
            console.log(response.data);
            navigate("/todos");
          })
          .catch((error) => console.error(error));
      }
    }
  }
  const { id } = useParams();
  function pageTitle() {
    if (id) {
      return <h2 className="text-center">Update Todo</h2>;
    } else {
      return <h2 className="text-center">Add Todo</h2>;
    }
  }

  useEffect(() => {
    if (id) {
      fetchTodo(id)
        .then((response) => {
          setTitle(response.data.title);
          setDescription(response.data.description);
          setCompleted(response.data.completed);
        })
        .catch((error) => console.error(error));
    }
  }, [id]);
  return (
    <div className="container">
      <br /> <br />
      <div className="row">
        <div className="card col-md-6 offset-md-3 offset-md-3">
          {pageTitle()}
          <div className="div-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">Todo Title:</label>
                <input
                  type="text"
                  placeholder="Enter Todo Title"
                  name="title"
                  value={title}
                  className={`form-control ${errors.title ? "is-invalid" : ""}`}
                  onChange={handleTitle}
                ></input>
                {errors.title && (
                  <div className="invalid-feedback"> {errors.title}</div>
                )}
              </div>
              <div className="form-group mb-2">
                <label className="form-label">Todo Description:</label>
                <input
                  type="text"
                  placeholder="Enter Todo Description"
                  name="descritpion"
                  value={description}
                  className={`form-control ${
                    errors.description ? "is-invalid" : ""
                  }`}
                  onChange={handleDescription}
                ></input>
                {errors.description && (
                  <div className="invalid-feedback"> {errors.description}</div>
                )}
              </div>

              <div className="form-group">
                <label className="form-label">Todo Completed:</label>
                <select
                  className="form-control"
                  id="exampleFormControlSelect1"
                  value={completed}
                  onChange={(e) => setCompleted(e.target.value)}
                >
                  <option value={false}>NO</option>
                  <option value={true}>YES</option>
                </select>
              </div>

              <button className="btn btn-success" onClick={handleAdd}>
                Save Details
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoComponent;
