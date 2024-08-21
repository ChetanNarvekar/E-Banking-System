import { configureStore } from "@reduxjs/toolkit";
import { rootReducer } from "../Reducer/reducer";

const store = configureStore({
  reducer: {
    root: rootReducer,
  },
});

export default store;
