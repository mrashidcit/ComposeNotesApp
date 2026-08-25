# Project instructions for Claude

## Git

Do not run `git commit` or `git push` (or any other action that changes
committed/remote history) unless the user explicitly asks for it in that
turn. Making edits to files is fine; staging and committing them is not,
even if it seems like the obvious next step to keep testing/CI moving.

When changes are ready, say what changed and that they're uncommitted, and
let the user decide when/whether to commit and push.
