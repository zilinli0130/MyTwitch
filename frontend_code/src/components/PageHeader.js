// Project includes
import Register from './Register'
import Login from './Login'
import Favorites from './Favorites'

// Framework includes
import React from 'react'
import { Layout, Row, Col, Button } from 'antd'
 
// Local Definition
const { Header } = Layout
 
// Component Definition
function PageHeader({ loggedIn, signoutOnClick, signinOnSuccess, favoriteItems  }) {
  return (
    <Header style={{ backgroundColor: "#9146FF"}}>
      <Row justify='space-between'>
        <Col>
          {loggedIn && <Favorites favoriteItems={favoriteItems} />}
        </Col>
        <Col>
          <div style={{ fontSize: 16, fontWeight: 600, color: "white", fontStyle: "italic" }}>
            MyTwitch Platform
          </div>
        </Col>
        <Col>
          {loggedIn && <Button shape="round" onClick={signoutOnClick}>Logout</Button>}
          {!loggedIn && (
            <>
              <Login onSuccess={signinOnSuccess} />
              <Register />
            </>
          )}
        </Col>
      </Row>
    </Header>
  )
}
 
export default PageHeader
