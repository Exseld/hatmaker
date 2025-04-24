import {createRoot} from 'react-dom/client'
import {BrowserRouter} from 'react-router-dom'
import {AuthentificatedProvider} from './context/AuthentificationContext.tsx'
import './index.css'
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
    <BrowserRouter>
        <AuthentificatedProvider>
            <App/>
        </AuthentificatedProvider>
    </BrowserRouter>
)
