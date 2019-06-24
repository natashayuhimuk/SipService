import React, { Fragment } from "react";

import TextField from "@material-ui/core/TextField";
import NumberFormat from "react-number-format";
import MenuItem from "@material-ui/core/MenuItem";

const TextFieldList = ({ conf = [], valid = [] }) => (
  <Fragment>
    {conf.map((item, i) =>
      item.title === "Phone Number" ? (
        <NumberFormat
          key={item.title}
          type="tel"
          label="Phone Number"
          customInput={TextField}
          value={item.value}
          onChange={e => item.setValue(e.target.value)}
          placeholder="+375 (##) ###-##-##"
          format="+375 (##) ###-##-##"
          mask="_"
          error={!valid[i]}
          fullWidth={true}
        />
      ) : (
        <TextField
          key={item.title}
          type={item.type || "text"}
          label={item.title}
          value={item.value}
          onChange={e => item.setValue(e.target.value)}
          margin="dense"
          error={!valid[i]}
          select={!!item.list}
          helperText={!valid[i] && item.message}
          fullWidth={true}
        >
          {item.list &&
            item.list.map(el =>
              item.title === "Tariff" ? (
                <MenuItem key={el.name} value={el.name}>
                  {el.name} ({el.cost})
                </MenuItem>
              ) : (
                <MenuItem key={el} value={el}>
                  {el}
                </MenuItem>
              )
            )}
        </TextField>
      )
    )}
  </Fragment>
);

export default TextFieldList;
