# Assumptions

- Artist creation and initial catalogue loading are handled upstream. This service starts with an existing artist catalogue.
- For local development convenience, the in-memory catalogue is seeded with a small hardcoded map of artists so the service can be tested without an extra create-artist API.
- A GET /artists endpoint returns the currently loaded artist catalogue from that in-memory map for the running process.
- Artist IDs are unique and stable for the lifetime of the in-memory process.
- The in-memory database is process-local and non-persistent. Restarting the application resets the hardcoded catalogue and clears all runtime-added data.
- The database singleton is intentional and shared by all application components.
- Track length is stored as a positive number of seconds.
- Genre is treated as caller-provided text; no fixed genre catalogue is assumed yet.
- Editing an artist name replaces the current name and does not create a separate alias history.
- Artist of the Day uses UTC dates and stable artist ID ordering so the rotation is deterministic and fair.
- An empty artist catalogue is an invalid state for Artist of the Day and raises an error.
- For invalid artist lookups, the API returns a 404 Not Found response when the artist does not exist.
- For invalid track creation input, the API returns a 400 Bad Request response when the title, genre, or length is missing or invalid.
- For invalid artist-name updates, the API returns a 400 Bad Request response when the artist name is blank or missing.
- For missing artist IDs, the API returns a 404 Not Found response for Add Track, Update Artist Name, and Fetch Artist Tracks.
- Fetching tracks for a valid artist returns a 200 OK response, and an empty track list is returned as an empty array rather than an error.
- Validation and not-found failures are handled at the controller layer by returning standard HTTP status responses.
- No authentication or authorization behavior is defined by the current requirements.
