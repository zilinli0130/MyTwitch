// Project includes
import {
  logout,
  getTopGames,
  getRecommendations,
  searchGameById,
  getFavoriteItem
} from './utils';
import CustomSearch from './components/CustomSearch';
import PageHeader from './components/PageHeader';
import Home from './components/Home';

// Framework includes
import { Layout, Menu, message } from 'antd';
import React, { useEffect, useState } from 'react';
import { LikeOutlined, FireOutlined } from '@ant-design/icons';
 
const { Sider, Content } = Layout
 
// Component definition
function App() {
  const [loggedIn, setLoggedIn] = useState(false)
  const [topGames, setTopGames] = useState([])
  const [resources, setResources] = useState({
    VIDEO: [],
    STREAM: [],
    CLIP: [],
  });
  const [favoriteItems, setFavoriteItems] = useState([]);
 
  useEffect(() => {
    getTopGames()
      .then((data) => {
        setTopGames(data)
      }).catch((err) => {
        message.error(err.message)
      })
  }, [])
 
  const signinOnSuccess = () => {
    setLoggedIn(true);
    getFavoriteItem().then((data) => {
      setFavoriteItems(data);
    });
  }
 
  const signoutOnClick = () => {
    logout().then(() => {
      setLoggedIn(false)
      message.success('Successfully Signed out')
    }).catch((err) => {
      message.error(err.message)
    })
  }
 
  const onGameSelect = ({ key }) => {
    if (key === "recommendation") {
      getRecommendations().then((data) => {
        setResources(data);
      });
 
      return;
    }
 
    searchGameById(key).then((data) => {
      setResources(data);
    });
  };
 
  const customSearchOnSuccess = (data) => {
    setResources(data);
  }
 
  const favoriteOnChange = () => {
    getFavoriteItem()
      .then((data) => {
        setFavoriteItems(data);
      })
      .catch((err) => {
        message.error(err.message);
      });
  };
 
  const mapTopGamesToProps = (topGames) => [
    {
      label: "Recommend for you!",
      key: "recommendation",
      icon: <LikeOutlined />,
    },
    {
      label: "Popular Games",
      key: "popular_games",
      icon: <FireOutlined />,
      children: topGames.map((game) => ({
        label: game.name,
        key: game.id,
        icon:
          <img
            alt="placeholder"
            src={game.box_art_url.replace('{height}', '40').replace('{width}', '40')}
            style={{ borderRadius: '50%', marginRight: '20px' }}
          />
      }))
    }
  ]
 
  return (
    <Layout style={{backgroundImage: `url("./background.jpg")`}}>
      <PageHeader
        loggedIn={loggedIn}
        signoutOnClick={signoutOnClick}
        signinOnSuccess={signinOnSuccess}
        favoriteItems={favoriteItems}
      />
      <Layout>
        <Sider width={300} style={{backgroundImage: `url("./slider_background.jpg")`, backgroundRepeat: "no-repeat", backgroundSize: "cover"}}>
          <CustomSearch onSuccess={customSearchOnSuccess}/>
          <Menu
            mode="inline"
            onSelect={onGameSelect}
            style={{ marginTop: '10px', opacity: 0.7}}
            items={mapTopGamesToProps(topGames)}
          />
        </Sider>
        <Layout style={{ padding: '24px', backgroundImage: `url("./background.jpg")`, backgroundSize: "cover"}}>
          <Content
            style={{
              padding: 24,
              margin: 0,
              height: 800,
              overflow: 'auto',
              opacity: 0.7,
              backgroundColor: "white"
            }}
          >
            <Home
              resources={resources}
              loggedIn={loggedIn}
              favoriteOnChange={favoriteOnChange}
              favoriteItems={favoriteItems}
            />
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
}
 
export default App;