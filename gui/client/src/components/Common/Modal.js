import React from "react";

import Modal from "@material-ui/core/Modal";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  paper: {
    position: "absolute",
    width: theme.spacing.unit * 50,
    maxWidth: "90%",
    boxShadow: theme.shadows[5],
    outline: "none",
    top: `50%`,
    left: `50%`,
    transform: `translate(-50%, -50%)`
  }
});

const SimpleModal = ({ classes, open = false, content }) => (
  <Modal open={open}>
    <div className={classes.paper}>{content}</div>
  </Modal>
);

export default withStyles(styles)(SimpleModal);
