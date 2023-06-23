// Project includes
import { searchGameByName } from '../utils'

// Framework includes
import React, { useState } from "react"
import { message, Button, Modal, Form, Input } from 'antd'
import { SearchOutlined } from '@ant-design/icons';
 
// Component Definition
function CustomSearch({ onSuccess }) {

  const [displayModal, setDisplayModal] = useState(false)
 
  const handleCancel = () => {
    setDisplayModal(false)
  }
 
  const searchOnClick = () => {
    setDisplayModal(true)
  }
 
  /*
  game name -> game_id -> {video:{games}, clip:{games}, stream:{games}}
  */
  const onSubmit = (data) => {
    searchGameByName(data.game_name).then((data) => {
      setDisplayModal(false)
      onSuccess(data)
    }).catch((err) => {
      message.error(err.message)
    })
  }
 
  return (
    <>
      <Button shape="round"
        onClick={searchOnClick}
        icon={<SearchOutlined />}
        style={{ marginLeft: '20px', marginTop: '20px', opacity: 0.7}}>
        Custom Search
      </Button>
      <Modal
        title="Search"
        visible={displayModal}
        onCancel={handleCancel}
        footer={null}
      >
        <Form
          name="custom_search"
          onFinish={onSubmit}
        >
          <Form.Item
            name="game_name"
            rules={[{ required: true, message: 'Please enter a game name' }]}
          >
            <Input placeholder="Game name" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              Search
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  )
 
}
 
export default CustomSearch