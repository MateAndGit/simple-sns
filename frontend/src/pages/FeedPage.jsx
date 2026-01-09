import { useState, useEffect } from "react";
import axios from "axios";

function FeedPage() {
  const [feeds, setFeeds] = useState([]);
  const [content, setContent] = useState("");
  const [targetId, setTargetId] = useState(""); // 팔로우할 대상 ID

  // 테스트를 위해 현재 로그인한 유저를 1번으로 가정
  const myId = 1;

  // 1. 타임라인 불러오기 (API 주소 변경됨: /timeline)
  const fetchFeeds = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/feeds/timeline?userId=${myId}`
      );
      setFeeds(response.data.content);
    } catch (error) {
      if (error.response?.status === 429) {
        alert("잠시만요! 요청이 너무 많습니다. 🛑");
      } else {
        console.error(error);
      }
    }
  };

  // 2. 글 작성하기
  const handlePost = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/feeds", {
        userId: myId,
        content: content,
        imageUrl: "https://via.placeholder.com/150",
      });
      setContent("");
      fetchFeeds(); // 작성 후 목록 갱신
    } catch (error) {
      alert("작성 실패: " + error.response?.data);
    }
  };

  // 3. 팔로우 하기 (새로 추가된 기능!)
  const handleFollow = async () => {
    if (!targetId) return alert("아이디를 입력해주세요!");
    try {
      // POST /api/follows/{targetId}?followerId={myId}
      await axios.post(
        `http://localhost:8080/api/follows/${targetId}?followerId=${myId}`
      );
      alert(`유저 ${targetId}번을 팔로우했습니다! 🎉`);
      setTargetId("");
      fetchFeeds(); // 팔로우했으니 타임라인 갱신
    } catch (error) {
      alert("팔로우 실패: " + (error.response?.data || "에러 발생"));
    }
  };

  useEffect(() => {
    fetchFeeds();
  }, []);

  return (
    <div style={{ padding: "20px", maxWidth: "600px", margin: "0 auto" }}>
      <h1>SNS 타임라인 🕒</h1>
      <p>현재 로그인 유저: {myId}번</p>

      {/* 팔로우 섹션 */}
      <div
        style={{
          background: "#f0f0f0",
          padding: "10px",
          borderRadius: "8px",
          marginBottom: "20px",
        }}
      >
        <h3>친구 찾기</h3>
        <input
          type="number"
          placeholder="팔로우할 유저 ID 입력"
          value={targetId}
          onChange={(e) => setTargetId(e.target.value)}
          style={{ marginRight: "10px", padding: "5px" }}
        />
        <button onClick={handleFollow}>팔로우</button>
      </div>

      {/* 글쓰기 섹션 */}
      <form
        onSubmit={handlePost}
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

      {/* 피드 목록 */}
      <div>
        {feeds.length === 0 ? (
          <p>게시글이 없습니다. 친구를 팔로우해보세요!</p>
        ) : null}
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
              {feed.nickname}{" "}
              <span style={{ fontSize: "0.8em", color: "#666" }}>
                ({new Date(feed.createdAt).toLocaleTimeString()})
              </span>
            </div>
            <div>{feed.content}</div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default FeedPage;
