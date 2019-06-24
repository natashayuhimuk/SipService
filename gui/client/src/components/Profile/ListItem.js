import React, { useEffect, useState } from "react";

import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Icon from "@material-ui/core/Icon";
import IconButton from "@material-ui/core/IconButton";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import MenuItem from "@material-ui/core/MenuItem";
import { unstable_useMediaQuery as useMediaQuery } from "@material-ui/core/useMediaQuery";
import { withStyles } from "@material-ui/core";

const styles = {
  item: {
    padding: "5px",
    display: "inline-block"
  },
  text: {
    display: "inline-block"
  },
  iconButton: {
    padding: "3px",
    marginLeft: "5px"
  },
  buttonsContainer: {
    display: "flex",
    alignItems: "flex-start"
  },
  primary: {
    fontSize: "15px",
    color: "#666",
    margin: 0
  },
  secondary: {
    fontSize: "20px",
    color: "#222",
    margin: 0
  },
  input: {
    fontSize: "20px",
    height: "29px"
  }
};

const ListItemProfile = ({
  classes,
  primary,
  secondary,
  setEditProfile,
  setState,
  valid = true,
  messageError = "",
  list = []
}) => {
  const [showButton, setShowButton] = useState(false);
  const [isEdit, setEdit] = useState(false);
  const matches = useMediaQuery("(max-width:600px)");

  useEffect(() => setShowButton(matches && valid), [matches, valid]);

  const onEdit = () => {
    setShowButton(false);
    setEdit(true);
    setEditProfile(true);
  };

  return (
    <ListItem className={classes.item}>
      <ListItemText
        onMouseEnter={() => {
          !isEdit && valid && setShowButton(true);
        }}
        onMouseLeave={() => !matches && setShowButton(false)}
        primary={
          <Typography component="span" className={classes.primary}>
            {primary}:
          </Typography>
        }
        secondary={
          <span className={classes.buttonsContainer}>
            {isEdit || !valid ? (
              <TextField
                autoFocus={true}
                value={secondary}
                onBlur={() => {
                  setEdit(false);
                  setShowButton(matches && valid);
                }}
                onChange={e => setState(e.target.value)}
                select={list.length > 0}
                error={!valid}
                InputProps={{
                  className: classes.input
                }}
                helperText={!valid && messageError}
              >
                {list.length > 0 &&
                  list.map(item => (
                    <MenuItem key={item.name} value={item.name}>
                      {item.name} ({item.cost})
                    </MenuItem>
                  ))}
              </TextField>
            ) : (
              <Typography component="span" className={classes.secondary}>
                {secondary}
              </Typography>
            )}
            {showButton ? (
              <IconButton className={classes.iconButton} onClick={onEdit}>
                <Icon fontSize="small">edit</Icon>
              </IconButton>
            ) : null}
          </span>
        }
        disableTypography={true}
        className={classes.text}
        classes={{
          primary: classes.primary,
          secondary: classes.secondary
        }}
      />
    </ListItem>
  );
};

export default withStyles(styles)(ListItemProfile);
