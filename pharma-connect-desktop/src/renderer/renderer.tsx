/**
 * This file will automatically be loaded by vite and run in the "renderer" context.
 * To learn more about the differences between the "main" and the "renderer" context in
 * Electron, visit:
 *
 * https://electronjs.org/docs/tutorial/application-architecture#main-and-renderer-processes
 *
 * By default, Node.js integration in this file is disabled. When enabling Node.js integration
 * in a renderer process, please be aware of potential security implications. You can read
 * more about security risks here:
 *
 * https://electronjs.org/docs/tutorial/security
 *
 * To enable Node.js integration in this file, open up `main.ts` and enable the `nodeIntegration`
 * flag:
 *
 * ```
 *  // Create the browser window.
 *  mainWindow = new BrowserWindow({
 *    width: 800,
 *    height: 600,
 *    webPreferences: {
 *      nodeIntegration: true
 *    }
 *  });
 * ```
 */

import "./index.css";
import { createRoot } from "react-dom/client";
import React from "react";
import {
  ChakraProvider,
  extendTheme,
  withDefaultColorScheme,
} from "@chakra-ui/react";
import App from "./App";
import { MemoryRouter } from "react-router-dom";
import { Provider as ReduxProvider } from "react-redux";
import { store } from "./redux/store";
const rootDiv = document.getElementById("root") as HTMLDivElement;
const root = createRoot(rootDiv);

const theme = extendTheme(
  withDefaultColorScheme({
    colorScheme: "teal",
  })
);

root.render(
  <ChakraProvider theme={theme}>
    <MemoryRouter>
      <ReduxProvider store={store}>
        <App />
      </ReduxProvider>
    </MemoryRouter>
  </ChakraProvider>
);

console.log(
  '👋 This message is being logged by "renderer.ts", included via Vite'
);
