import React, { useState } from "react";
import classNames from "classnames";

import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import Icon from "@material-ui/core/Icon";
import Tooltip from "@material-ui/core/Tooltip";
import InputBase from "@material-ui/core/InputBase";
import { lighten } from "@material-ui/core/styles/colorManipulator";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  root: {
    paddingRight: theme.spacing.unit
  },
  highlight: {
    color: theme.palette.secondary.main,
    backgroundColor: lighten(theme.palette.secondary.light, 0.85)
  },
  spacer: {
    flex: "1 1 100%"
  },
  actions: {
    color: theme.palette.text.secondary,
    display: "flex"
  },
  title: {
    flex: "0 0 auto"
  },
  input: {
    flex: 1,
    fontSize: "20px",
    padding: "10px 0"
  }
});

const TableToolbar = ({ selected, classes, buttons, title, setSearch }) => {
  const [visibleSearch, setVisibleSearch] = useState(false);
  const [searchText, setSearchText] = useState("");

  const closeSearch = () => {
    setVisibleSearch(false);
    setSearchText("");
    setSearch("");
  };

  const onKeySearch = e => {
    if (e.keyCode === 13) {
      setSearch(searchText);
    }
    if (e.keyCode === 27) {
      closeSearch();
    }
  };

  return visibleSearch ? (
    <Toolbar className={classes.root}>
      <InputBase
        className={classes.input}
        placeholder="Search"
        value={searchText}
        onChange={e => setSearchText(e.target.value)}
        autoFocus={true}
        onKeyDown={onKeySearch}
      />
      {selected.length === 1 &&
        buttons.oneSelected &&
        buttons.oneSelected.map(item => (
          <Tooltip title={item.title} key={item.title}>
            <IconButton onClick={() => item.func(selected)}>
              <Icon>{item.icon}</Icon>
            </IconButton>
          </Tooltip>
        ))}
      {selected.length > 0 &&
        buttons.selected.map(item => (
          <Tooltip title={item.title} key={item.title}>
            <IconButton
              onClick={
                selected.length > 0
                  ? () => {
                      item.func(selected);
                    }
                  : item.func
              }
            >
              <Icon>{item.icon}</Icon>
            </IconButton>
          </Tooltip>
        ))}
      <Tooltip title="Search">
        <IconButton onClick={() => setSearch(searchText)}>
          <Icon>search</Icon>
        </IconButton>
      </Tooltip>
      <Tooltip title="Close">
        <IconButton onClick={closeSearch}>
          <Icon>close</Icon>
        </IconButton>
      </Tooltip>
    </Toolbar>
  ) : (
    <Toolbar
      className={classNames(classes.root, {
        [classes.highlight]: selected.length > 0
      })}
    >
      <div className={classes.title}>
        {selected.length > 0 ? (
          <Typography color="inherit" variant="subtitle1">
            {selected.length} selected
          </Typography>
        ) : (
          <Typography variant="h6" id="tableTitle">
            {title}
          </Typography>
        )}
      </div>
      <div className={classes.spacer} />
      <div className={classes.actions}>
        <Tooltip title={"Search"}>
          <IconButton onClick={() => setVisibleSearch(true)}>
            <Icon>search</Icon>
          </IconButton>
        </Tooltip>
        {selected.length === 1 &&
          buttons.oneSelected &&
          buttons.oneSelected.map(item => (
            <Tooltip title={item.title} key={item.title}>
              <IconButton onClick={() => item.func(selected)}>
                <Icon>{item.icon}</Icon>
              </IconButton>
            </Tooltip>
          ))}
        {buttons[selected.length > 0 ? "selected" : "main"].map(item => (
          <Tooltip title={item.title} key={item.title}>
            <IconButton
              onClick={
                selected.length > 0
                  ? () => {
                      item.func(selected);
                    }
                  : item.func
              }
            >
              <Icon>{item.icon}</Icon>
            </IconButton>
          </Tooltip>
        ))}
      </div>
    </Toolbar>
  );
};

export default withStyles(styles)(TableToolbar);
