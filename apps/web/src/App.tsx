import { Route, Routes } from 'react-router-dom';
import { SystemStatusPage } from './pages/SystemStatusPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<SystemStatusPage />} />
    </Routes>
  );
}

export default App;
