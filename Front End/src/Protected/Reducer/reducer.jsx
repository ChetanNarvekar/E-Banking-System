import { createReducer } from "@reduxjs/toolkit";
import { login, logout } from "../Action/action";


export const rootReducer = createReducer(
  { isAuthenticated: false },
  (builder) => {
    builder
      .addCase(login, (state) => {
        state.isAuthenticated = true;
      })
      .addCase(logout, (state) => {
        state.isAuthenticated = false;
      });
  }
);
