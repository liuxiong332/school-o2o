import React, { useState, useCallback } from 'react';
import { App, View, Page, List, ListInput, ListButton, Button } from 'framework7-react';
import { request } from "./common";

// home.jsx
export default () => {
  let [email, setEmail] = useState("");
  let [username, setUsername] = useState("");
  let [password, setPassword] = useState("");
  let [verificationCode, setCode] = useState("");

  const handleEmail = useCallback(event => {
    setEmail(event.target.value);
  });
  const handleUsername = useCallback(event => {
    setUsername(event.target.value);
  });
  const handlePassword = useCallback(event => {
    setPassword(event.target.value);
  });
  const handleCode = useCallback(event => {
    setCode(event.target.value);
  });

  const handleSendCode = useCallback(() => {
    request(`/sendVerifyCode?email=${email}`, { method: "POST" });
  }, [email]);

  const handleSignup = useCallback(() => {
    let body = {
      email, username, password, verificationCode
    };
    request(`/signup`, { method: "POST", body });
  });
  return (
    <Page name="home">
      <List noHairlinesMd>
        <ListInput
          label="E-mail"
          type="email"
          placeholder="Your e-mail"
          value={email}
          onChange={handleEmail}
          clearButton
        />

        <ListInput
          label="Email verification code"
          type="text"
          placeholder="verification code"
          value={verificationCode}
          onChange={handleCode}
          clearButton
        />
        <ListButton title="Send Verification Code" onClick={handleSendCode}/>

        <ListInput
          label="Name"
          type="text"
          placeholder="Your name"
          value={username}
          onChange={handleUsername}
          clearButton
        />

        <ListInput
          label="Enter Password"
          type="password"
          placeholder="Your password"
          value={password}
          onChange={handlePassword}
          clearButton
        />
        
      </List>

      <Button fill onClick={handleSignup}>Signup</Button>
    </Page>
  );
};