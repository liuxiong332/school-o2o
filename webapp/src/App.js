import React from 'react';
import logo from './logo.svg';
import 'framework7/css/framework7.bundle.css';
import './App.css';
import { App, View, Page, Navbar, Toolbar, Link } from 'framework7-react';
import HomePage from "./Signup";
import LoginPage from "./Login";

const f7params = {
  // Array with app routes
  // routes,
  // App Name
  name: 'My App',
  // App id
  id: 'com.myapp.test',
  // ...

  routes: [
    {
      path: '/login',
      component: LoginPage,
    },
    {
      path: '/signup',
      component: HomePage,
    },
    // {
    //   path: '/about/',
    //   component: AboutPage,
    // },
    // {
    //   path: '/login/',
    //   component: LoginPage,
    // },
  ],
};

function MyApp() {
  return (
    <App params={f7params}>

    {/* Your main view, should have "main" prop */}
    <View main url="/login">
    </View>
  </App>
  );
}

export default MyApp;
