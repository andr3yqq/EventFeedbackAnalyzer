import './App.css';

import {useEffect, useState} from 'react';


function App() {
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [newEvent, setNewEvent] = useState({});
  const [eventFeedback, setEventFeedback] = useState([]);
  const [feedbackToEvent, setFeedbackToEvent] = useState(null);
  const [feedbackSummary, setFeedbackSummary] = useState([]);
  const [newFeedbackUsername, setNewFeedbackUsername] = useState('');
  const [newFeedbackText, setNewFeedbackText] = useState('');
  const [ratings, setRatings] = useState({});
  const [mainToggle, setMainToggle] = useState(""); //viewEvents, addEvent
  const [toggle, setToggle] = useState(""); //viewFeedback, addFeedback, summary

  const loadEvents = () => {
    const requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
    }
    fetch('http://localhost:8080/events', requestOptions)
      .then(response => response.json())
      .then(data => {
        setEvents(data);
      })
      .catch(error => console.log('error', error));
  }

  const loadEventFeedback = (eventId) => {
    const requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
    }
    fetch(`http://localhost:8080/events/${eventId}/feedback`, requestOptions)
      .then(response => response.json())
      .then(data => {
        setEventFeedback(data);
      })
        .catch(error => console.log('error', error));
  }

  const submitFeedback = (e) => {
    e.preventDefault();

    const feedbackPayload = {
      username: newFeedbackUsername,
      feedback: newFeedbackText,
    };

    fetch(`http://localhost:8080/events/${feedbackToEvent.id}/feedback`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(feedbackPayload),
    })
        .then(res => {
          if (!res.ok) throw new Error('Failed to submit feedback');
          return res.json();
        })
        .then(createdFeedback => {
          loadEventFeedback(feedbackToEvent.id);
          setFeedbackToEvent(null);
          setNewFeedbackUsername('');
          setNewFeedbackText('');
          setToggle("");
        })
        .catch(err => console.error(err));
  };

  const loadFeedbackSummary = (event) => {
    setToggle("summary");
    fetch(`http://localhost:8080/events/${event.id}/summary`)
        .then(res => res.json())
        .then(data => {
          setFeedbackSummary(data);
          setRatings(data.ratings);
        });
    setSelectedEvent(event);
  };

  const handleAddEvent = (e) => {
    e.preventDefault();

    fetch('http://localhost:8080/events', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newEvent),
    })
        .then(res => {
          if (!res.ok) throw new Error('Failed to create event');
          return res.json();
        })
        .then(createdEvent => {
          setEvents(prev => [...prev, createdEvent]);
          setNewEvent({ title: '', description: '' });
        })
        .catch(error => alert(error.message));
  };
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewEvent(prev => ({ ...prev, [name]: value }));
    };

  const showEventFeedback = (event) => {
    setSelectedEvent(event);
    setToggle("viewFeedback");
  }

  useEffect(() => {
    loadEvents();
  },[]);

  useEffect(() => {
    if (selectedEvent !== null) {
      loadEventFeedback(selectedEvent.id);
    }
  },[selectedEvent]);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Event Feedback Analyzer</h1>
      </header>
      <nav className="App-nav">
        <h2 onClick={() => setMainToggle("viewEvents")}>Events</h2>
        <h2 onClick={() => setMainToggle("addEvent")}>Add event</h2>
      </nav>
      <main className="App-main">
        {mainToggle === "addEvent" && (
            <>
                <h3>New event</h3>
                <form onSubmit={handleAddEvent}>
                    <input
                        name="title"
                        placeholder="Event Title"
                        value={newEvent.title}
                        onChange={handleInputChange}
                        required
                    />
                    <input
                        name="description"
                        placeholder="Event Description"
                        value={newEvent.description}
                        onChange={handleInputChange}
                        required
                    />
                    <button type="submit">Add Event</button>
                </form>
            </>
        )}
        {mainToggle === "viewEvents" && (
            <>
              <table>
                <thead>
                <tr>
                  <th>Event title</th>
                  <th>Event description</th>
                </tr>
                </thead>
                <tbody>
                {events.map(event => (
                    <tr key={event.id}>
                      <td className="table-title">{event.title}</td>
                      <td className="table-desc">{event.description}</td>
                      <td className="table-actions">
                        <button
                            onClick={() => showEventFeedback(event)}>View feedback</button>
                        <button
                            onClick={() => {
                              setFeedbackToEvent(event);
                              setToggle("addFeedback");
                            }}>Add feedback</button>
                        <button onClick={() => loadFeedbackSummary(event)}>Feedback summary</button>
                      </td>
                    </tr>
                ))}
                </tbody>
              </table>
              {toggle === "viewFeedback" && (
                  <section style={{ marginTop: '2rem' }}>
                    <h2>Feedback for event: {selectedEvent.title}</h2>
                    {eventFeedback.length === 0 ? (
                        <p>No feedback yet.</p>
                    ) : (
                        <ul>
                          {eventFeedback.map(feedback => (
                              <li key={feedback.id}>
                                <strong>{feedback.username}</strong>: {feedback.feedback} <em>({new Date(feedback.timestamp).toLocaleString()})</em>
                              </li>
                          ))}
                        </ul>
                    )}
                  </section>
              )}
              {toggle === "addFeedback" && (
                  <section>
                    <h2>Add feedback for event: {feedbackToEvent.title}</h2>
                    <form onSubmit={submitFeedback}>
                      <label>
                        Username:
                        <input
                            type="text"
                            value={newFeedbackUsername}
                            onChange={e => setNewFeedbackUsername(e.target.value)}
                            required
                        />
                      </label>
                      <label>
                        Feedback:
                        <textarea
                            value={newFeedbackText}
                            onChange={e => setNewFeedbackText(e.target.value)}
                            required
                        />
                      </label>
                      <button type="submit">Submit feedback</button>
                    </form>
                  </section>
              )}
              {toggle === "summary" && (
                  <section>
                    <h2>Feedback summary for event: {selectedEvent.title}</h2>
                    <ul>
                      <li>Very Positive: {ratings["Very Positive"]}</li>
                      <li>Positive: {ratings["Positive"]}</li>
                      <li>Neutral: {ratings["Neutral"]}</li>
                      <li>Negative: {ratings["Negative"]}</li>
                      <li>Very Negative: {ratings["Very Negative"]}</li>
                    </ul>
                  </section>
              )}
            </>
        )}

      </main>
      <footer className="App-footer">
        <p>
          <a href="https://github.com/andr3yqq/EventFeedbackAnalyzer">
            Github
          </a>
        </p>
      </footer>
    </div>
  );
}

export default App;
