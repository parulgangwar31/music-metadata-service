# Assumptions

- Artist creation and initial catalogue loading are handled upstream. This service starts with an existing artist catalogue.
- Artist IDs are unique and stable for the lifetime of the in-memory process.
- The in-memory database is process-local and non-persistent. Restarting the application clears all data.
- The database singleton is intentional and shared by all application components.
- Track length is stored as a positive number of seconds.
- Genre is treated as caller-provided text; no fixed genre catalogue is assumed yet.
- Editing an artist name replaces the current name and does not create a separate alias history.
- Artist of the Day uses UTC dates and stable artist ID ordering so the rotation is deterministic and fair.
- An empty artist catalogue is an invalid state for Artist of the Day and raises an error.
- Unknown artist IDs raise an error from the database layer. HTTP error mapping will be added when error handling is implemented.
- No authentication or authorization behavior is defined by the current requirements.
