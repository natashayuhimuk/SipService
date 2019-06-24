import React from "react";
import classNames from "classnames";
import { withStyles } from "@material-ui/core";

const styles = {
  foldingCube: {
    display: "inline-block",
    width: "60px",
    height: "60px",
    position: "relative",
    transform: "rotateZ(45deg)"
  },
  cube: {
    float: "left",
    width: "50%",
    height: "50%",
    position: "relative",
    transform: "scale(1.1)",
    "&:before": {
      content: "''",
      position: "absolute",
      top: 0,
      left: 0,
      width: "100%",
      height: "100%",
      backgroundColor: "#3f51b5",
      animation: "foldCubeAngle 2.4s infinite linear both",
      transformOrigin: "100% 100%"
    }
  },
  cube2: {
    transform: "scale(1.1) rotateZ(90deg)",
    "&:before": {
      animationDelay: "0.3s"
    }
  },
  cube3: {
    transform: "scale(1.1) rotateZ(180deg)",
    "&:before": {
      animationDelay: "0.6s"
    }
  },
  cube4: {
    transform: "scale(1.1) rotateZ(270deg)",
    "&:before": {
      animationDelay: "0.9s"
    }
  },
  "@keyframes foldCubeAngle": {
    "0%, 10%": {
      transform: "perspective(140px) rotateX(-180deg)",
      opacity: 0
    },
    "25%, 75%": {
      transform: "perspective(140px) rotateX(0deg)",
      opacity: 1
    },
    "90%, 100%": {
      transform: "perspective(140px) rotateY(180deg)",
      opacity: 0
    }
  }
};

const Spinner = ({ classes }) => (
  <div className={classes.foldingCube}>
    <div className={classes.cube} />
    <div className={classNames(classes.cube, classes.cube2)} />
    <div className={classNames(classes.cube, classes.cube4)} />
    <div className={classNames(classes.cube, classes.cube3)} />
  </div>
);

export default withStyles(styles)(Spinner);
