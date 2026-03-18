import { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const CreateUserModal = ({ show, handleClose, onSubmit }) => {
    const [form, setForm] = useState({
        username: "",
        email: "",
        password: "",
        fullName: "",
        roles: []
    });

    const rolesList = [
        "PLATFORM_ADMIN",
        "COURSE_AUTHOR",
        "CONTENT_ADMIN",
        "LEARNER"
    ];

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleRoleChange = (role) => {
        setForm(prev => {
            const exists = prev.roles.includes(role);
            return {
                ...prev,
                roles: exists
                    ? prev.roles.filter(r => r !== role)
                    : [...prev.roles, role]
            };
        });
    };

    const handleSubmit = () => {
        if (!form.username || !form.email || !form.password) {
            alert("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        onSubmit(form);
        handleClose();
    };

    return (
        <Modal show={show} onHide={handleClose} centered>
            <Modal.Header closeButton>
                <Modal.Title>Create User</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            name="username"
                            value={form.username}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            name="email"
                            value={form.email}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            name="password"
                            value={form.password}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Full Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="fullName"
                            value={form.fullName}
                            onChange={handleChange}
                        />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Roles</Form.Label>
                        {rolesList.map(role => (
                            <Form.Check
                                key={role}
                                type="checkbox"
                                label={role.replaceAll("_", " ")}
                                checked={form.roles.includes(role)}
                                onChange={() => handleRoleChange(role)}
                            />
                        ))}
                    </Form.Group>
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Cancel
                </Button>
                <Button variant="primary" onClick={handleSubmit}>
                    Create
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default CreateUserModal;