import { useState } from "react";
import axios from 'axios';
import { toast } from 'react-toastify';
import { Modal, Button, Form } from 'react-bootstrap';
import config from "../../../../config";

const VerifyTpinModal = ({ show, handleClose, onVerify }) => {
    const [tpin, setTpin] = useState('');

    const handleVerify = async () => {
        const customerId = sessionStorage.getItem('customerId');

        if (!customerId) {
            toast.error("No customer ID found in session storage");
            return;
        }

        try {
            // POST request with TPIN as a request parameter
            const token=sessionStorage.getItem('token');
            await axios.post(`${config.url}/bank/user/verify-tpin/${customerId}?tpin=${tpin}`, null, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            toast.success("TPIN verified successfully");
            onVerify(); // Proceed after successful verification
            handleClose(); // Close the modal
            setTpin(''); // Clear the TPIN input field
        } catch (error) {
            if (error.response) {
                toast.error(`Error: ${error.response.data}`);
            } else if (error.request) {
                toast.error('No response from server');
            } else {
                toast.error(`Error: ${error.message}`);
            }
        }
    };

    const handleModalClose = () => {
        setTpin(''); // Clear the TPIN input when modal is closed
        handleClose(); // Call the parent's handleClose to close the modal
    };

    return (
        <Modal show={show} onHide={handleModalClose}>
            <Modal.Header closeButton>
                <Modal.Title>Verify TPIN</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group controlId="formTpin">
                        <Form.Label>TPIN</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Enter TPIN"
                            value={tpin}
                            onChange={(e) => setTpin(e.target.value)}
                            required
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleModalClose}>
                    Cancel
                </Button>
                <Button variant="dark" onClick={handleVerify}>
                    Verify
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default VerifyTpinModal;
