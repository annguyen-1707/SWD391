import UserTable from "../components/UserTable"
import { useEffect, useState } from "react";
import { deleteUserAPI, getUserWithPaginateAPI, createUserAPI } from "../services/UserService";
import { toast } from "react-toastify"; import PaginationCustom from "@/features/admin/users/components/Pagination";
import SearchItem from "@/features/admin/users/components/SearchItem";
import "./UserPage.css";
import UpdateUser from "../components/UpdateUser";
import DeleteConfirmModal from "../components/DeleteConfirmModal";
import { Button, Col, Row } from "react-bootstrap";
import CreateUserModal from "../components/CreateUserModal";
const UserPage = () => {
    const [users, setUsers] = useState([]);

    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(5);

    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);

    const [loading, setLoading] = useState(false);

    const [debouncedSearch, setDebouncedSearch] = useState("");
    const [search, setSearch] = useState("");

    const [show, setShow] = useState(false);
    const [selectedUser, setSelectedUSer] = useState("")

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [userToDelete, setUserToDelete] = useState(null);

    const [showModal, setShowModal] = useState(false);

    const [formCreate, setFormCreate] = useState(
        {
            username: "",
            email: "",
            password: "",
            fullName: "",
            roles: []
        }
    )

    const handleUpdateUser = (user) => {
        setSelectedUSer(user)
        setShow(true)
    }
    const onClose = () => {
        setShow(false)
    }
    const fetchUsers = async () => {

        try {

            setLoading(true);

            const res = await getUserWithPaginateAPI(currentPage, pageSize, debouncedSearch);
            console.log("Fetched users:", res);

            const data = res.data.data;

            setUsers(data.content);
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);

        } catch (error) {

            console.error(error);

        } finally {

            setLoading(false);

        }
    };
    const handleDeleteUser = (user) => {
        setUserToDelete(user);
        setShowDeleteModal(true);
    };
    const confirmDeleteUser = async () => {

        try {

            await deleteUserAPI(userToDelete.id);

            fetchUsers();

            toast.success("User deleted successfully");

        } catch (error) {

            toast.error("Delete failed");

        } finally {

            setShowDeleteModal(false);

        }
    };

    const handleSubmit = async (data) => {
        try {
            const res = await createUserAPI(data);
            toast.success("Create User " + res.fullName + " successful")
            fetchUsers();
        } catch (error) {
            toast.error("Fail to create")
        }
    }
    useEffect(() => {
        fetchUsers();
    }, [currentPage, pageSize, debouncedSearch]);

    useEffect(() => {

        const timer = setTimeout(() => {
            setDebouncedSearch(search);
            setCurrentPage(0)

        }, 300);
        return () => clearTimeout(timer);

    }, [search]);

    return (
        <>
            <div className="page-container">
                <DeleteConfirmModal
                    show={showDeleteModal}
                    user={userToDelete}
                    onClose={() => setShowDeleteModal(false)}
                    onConfirm={confirmDeleteUser}
                />
                <UpdateUser
                    selectedUser={selectedUser}
                    show={show}
                    onClose={onClose}
                    fetchUsers={fetchUsers}

                />

                <CreateUserModal
                    show={showModal}
                    handleClose={() => setShowModal(false)}
                    onSubmit={(data) => {
                        handleSubmit(data)
                    }}
                />
                <Row className="d-flex justify-content-between align-items-center">
                    <Col xs="auto">
                        <SearchItem
                            search={search}
                            onSearch={setSearch}
                        />
                    </Col>

                    <Col xs="auto">
                        <Button onClick={() => setShowModal(true)}>
                            Create User
                        </Button>
                    </Col>
                </Row>


                <UserTable
                    users={users}
                    loading={loading}
                    handleUpdateUser={handleUpdateUser}
                    handleDeleteUser={handleDeleteUser}
                />

                <PaginationCustom
                    currentPage={currentPage}
                    pageSize={pageSize}
                    totalPages={totalPages}
                    totalElements={totalElements}
                    onPageChange={setCurrentPage}
                />
            </div>
        </>
    )
}
export default UserPage
