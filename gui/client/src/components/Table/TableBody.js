import React from "react";

import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Checkbox from "@material-ui/core/Checkbox";

import { commonService } from "../../services";

const MainTableBody = ({ data, fieldId, keys, isSelected, handleClick }) => (
  <TableBody>
    {data.map(n => (
      <TableRow
        key={n[fieldId]}
        hover
        onClick={event => handleClick(event, n[fieldId])}
        role="checkbox"
        selected={isSelected(n[fieldId])}
      >
        <TableCell padding="checkbox" style={{ width: "80px" }}>
          <Checkbox checked={isSelected(n[fieldId])} />
        </TableCell>
        {keys.map(key => (
          <TableCell key={key} style={{ whiteSpace: "nowrap" }}>
            {key === "phoneNumber"
              ? commonService.parseNumber(n[key])
              : key === "tariff"
              ? n[key].name
              : n[key]}
          </TableCell>
        ))}
      </TableRow>
    ))}
  </TableBody>
);

export default MainTableBody;
