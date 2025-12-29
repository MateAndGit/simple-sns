import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function SignupPage() {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
    nickname: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/users/signup", formData);
      alert("회원가입 성공!");
      navigate("/");
    } catch (error) {
      alert("회원가입 실패: " + (error.response?.data || "알수없는 오류"));
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>회원가입</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            name="email"
            placeholder="이메일"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div style={{ marginTop: "10px" }}>
          <input
            name="password"
            type="password"
            placeholder="비밀번호"
            value={formData.password}
            onChange={handleChange}
          />
        </div>
        <div style={{ marginTop: "10px" }}>
          <input
            name="nickname"
            placeholder="닉네임"
            value={formData.nickname}
            onChange={handleChange}
          />
        </div>
        <button type="submit" style={{ margin: "20px" }}>
          가입하기
        </button>
      </form>
    </div>
  );
}
