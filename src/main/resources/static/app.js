const state = {
    auth: null,
};

const authScreen = document.getElementById('auth-screen');
const appScreen = document.getElementById('app-screen');
const authMessageEl = document.getElementById('auth-message');
const messageEl = document.getElementById('message');
const loginStatusEl = document.getElementById('login-status');
const booksTableBody = document.getElementById('books-table-body');
const tabButtons = document.querySelectorAll('.tab-btn');
const loginPanel = document.getElementById('login-panel');
const registerPanel = document.getElementById('register-panel');

function showMessage(text, type = 'info', target = 'app') {
    const targetEl = target === 'auth' ? authMessageEl : messageEl;
    targetEl.textContent = text;
    targetEl.style.color = type === 'error' ? '#fb7185' : '#cbd5e1';
}

function getAuthHeader() {
    if (!state.auth) {
        return { 'Content-Type': 'application/json' };
    }
    return {
        Authorization: 'Basic ' + btoa(`${state.auth.email}:${state.auth.password}`),
        'Content-Type': 'application/json',
    };
}

function saveAuth(email, password) {
    state.auth = { email, password };
    sessionStorage.setItem('bookAppAuth', JSON.stringify(state.auth));
    updateLoginStatus();
}

function clearAuth() {
    state.auth = null;
    sessionStorage.removeItem('bookAppAuth');
    updateLoginStatus();
    showScreen('auth');
}

function updateLoginStatus() {
    if (state.auth) {
        loginStatusEl.textContent = `Logged in as ${state.auth.email}`;
    } else {
        loginStatusEl.textContent = 'Not logged in.';
    }
}

function showScreen(screen) {
    if (screen === 'app') {
        authScreen.classList.add('hidden');
        appScreen.classList.remove('hidden');
        showMessage('Welcome back. Your book collection is ready.');
    } else {
        appScreen.classList.add('hidden');
        authScreen.classList.remove('hidden');
    }
}

function activateTab(tabName) {
    tabButtons.forEach(button => {
        button.classList.toggle('active', button.dataset.tab === tabName);
    });
    loginPanel.classList.toggle('active', tabName === 'login');
    registerPanel.classList.toggle('active', tabName === 'register');
}

async function fetchBooks() {
    try {
        const response = await fetch('/api/books');
        if (!response.ok) {
            throw new Error('Unable to load book list');
        }
        const books = await response.json();
        booksTableBody.innerHTML = books
            .map(book => `
            <tr>
                <td>${book.id || ''}</td>
                <td>${book.title || ''}</td>
                <td>${book.author || ''}</td>
                <td>${book.genre || ''}</td>
                <td>${book.createdAt ? new Date(book.createdAt).toLocaleString() : ''}</td>
                <td>${book.updateTime ? new Date(book.updateTime).toLocaleString() : ''}</td>
            </tr>
        `)
            .join('');
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

async function fetchBookById(id) {
    try {
        const response = await fetch(`/api/getbook/${id}`);
        if (!response.ok) {
            throw new Error('Book not found');
        }
        const book = await response.json();
        document.getElementById('book-id').value = book.id || '';
        document.getElementById('book-title').value = book.title || '';
        document.getElementById('book-author').value = book.author || '';
        document.getElementById('book-genre').value = book.genre || '';
        showMessage(`Loaded book ${book.id}`);
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

async function registerUser(event) {
    event.preventDefault();
    const data = {
        email: document.getElementById('register-email').value.trim(),
        password: document.getElementById('register-password').value,
        firstName: document.getElementById('register-firstName').value.trim(),
        lastName: document.getElementById('register-lastName').value.trim(),
    };

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Registration failed');
        }
        const user = await response.json();
        showMessage(`Registered ${user.email}. You may now login.`, 'info', 'auth');
        document.getElementById('register-form').reset();
        activateTab('login');
    } catch (error) {
        showMessage(error.message, 'error', 'auth');
    }
}

async function loginUser(event) {
    event.preventDefault();
    const email = document.getElementById('login-email').value.trim();
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password }),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Login failed');
        }
        await response.json();
        saveAuth(email, password);
        document.getElementById('login-form').reset();
        activateTab('login');
        showScreen('app');
        fetchBooks();
    } catch (error) {
        showMessage(error.message, 'error', 'auth');
    }
}

async function saveBook(event) {
    event.preventDefault();
    const payload = {
        title: document.getElementById('book-title').value.trim(),
        author: document.getElementById('book-author').value.trim(),
        genre: document.getElementById('book-genre').value.trim(),
    };
    try {
        const response = await fetch('/api/addbook', {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(payload),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Failed to create book');
        }
        await response.json();
        showMessage('Book created successfully.');
        fetchBooks();
        document.getElementById('book-form').reset();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

async function updateBook() {
    const id = document.getElementById('book-id').value.trim();
    if (!id) {
        showMessage('Enter a book ID to update.', 'error');
        return;
    }
    const payload = {
        title: document.getElementById('book-title').value.trim(),
        author: document.getElementById('book-author').value.trim(),
        genre: document.getElementById('book-genre').value.trim(),
    };
    try {
        const response = await fetch(`/api/updatebook/${id}`, {
            method: 'PUT',
            headers: getAuthHeader(),
            body: JSON.stringify(payload),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Failed to update book');
        }
        await response.json();
        showMessage('Book updated successfully.');
        fetchBooks();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

async function deleteBook() {
    const id = document.getElementById('book-id').value.trim();
    if (!id) {
        showMessage('Enter a book ID to delete.', 'error');
        return;
    }
    try {
        const response = await fetch(`/api/deletebook/${id}`, {
            method: 'DELETE',
            headers: getAuthHeader(),
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Failed to delete book');
        }
        await response.json();
        showMessage('Book deleted successfully.');
        fetchBooks();
        document.getElementById('book-form').reset();
    } catch (error) {
        showMessage(error.message, 'error');
    }
}

function loadStoredAuth() {
    const raw = sessionStorage.getItem('bookAppAuth');
    if (!raw) return;
    try {
        state.auth = JSON.parse(raw);
        updateLoginStatus();
    } catch {
        state.auth = null;
    }
}

function attachEventListeners() {
    document.getElementById('register-form').addEventListener('submit', registerUser);
    document.getElementById('login-form').addEventListener('submit', loginUser);
    document.getElementById('book-form').addEventListener('submit', saveBook);
    document.getElementById('refresh-books').addEventListener('click', fetchBooks);
    document.getElementById('search-book').addEventListener('click', () => {
        const id = document.getElementById('book-id-search').value.trim();
        if (!id) {
            showMessage('Enter a book ID to search.', 'error');
            return;
        }
        fetchBookById(id);
    });
    document.getElementById('update-book').addEventListener('click', updateBook);
    document.getElementById('delete-book').addEventListener('click', deleteBook);
    document.getElementById('logout-button').addEventListener('click', () => {
        clearAuth();
        showMessage('Logged out.');
    });

    tabButtons.forEach(button => {
        button.addEventListener('click', () => activateTab(button.dataset.tab));
    });
}

window.addEventListener('DOMContentLoaded', () => {
    loadStoredAuth();
    attachEventListeners();
    if (state.auth) {
        showScreen('app');
        fetchBooks();
    } else {
        showScreen('auth');
    }
});
