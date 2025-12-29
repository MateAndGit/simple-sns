import axios from "axios";
import { useEffect, useState } from "react";

export default function FeedPage() {
  const [feeds, setFeeds] = useState([]);
  const [content, setContent] = useState("");

  const userId = 1;

  const fetchFeeds = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/feeds?userId=${userId}`
      );
      setFeeds(response.data.content);
    } catch (error) {
      if (error.response?.status === 429) {
        alert("잠시만요! 요청이 너무 많습니다. 1분 뒤에 시도해주세요. 🛑");
      } else {
        console.log(error);
      }
    }
  };

  useEffect(() => {
    fetchFeeds();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/feeds", {
        userId: userId,
        content: content,
      });
      setContent("");
      fetchFeeds();
    } catch (error) {
      if (error.response?.status === 429) {
        alert("글을 너무 빨리 쓰고 계시네요! 조금만 쉬었다 쓰세요. 🛑");
      } else {
        alert("작성 실패 ㅠㅠ");
      }
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "600px", margin: "0 outo" }}>
      <h1>내 타임라인 🕧</h1>

      <form
        onSubmit={handleSubmit}
        style={{ marginBottom: "20px", display: "flex", gap: "10px" }}
      >
        <input
          style={{ flex: 1, padding: "10px" }}
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="무슨 생각을 하고 계신가요?"
        />
        <button type="submit">게시</button>
      </form>

      <div>
        {feeds.map((feed) => (
          <div
            key={feed.id}
            style={{
              border: "1px solid #ddd",
              padding: "15px",
              marginBottom: "10px",
              borderRadius: "8px",
            }}
          >
            <div style={{ fontWeight: "bold", marginBottom: "5px" }}>
              {feed.nickname}
            </div>
            <div>{feed.content}</div>
            <div style={{ fontSize: "12px", color: "#888", marginTop: "10px" }}>
              {new Date(feed.createdAt).toLocaleString()}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
