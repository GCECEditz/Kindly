package com.example.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// ============================================================
// UI-ONLY MOCK DATA for the Proposal module (1.0).
// TODO(1.x backend): replace with Supabase D1 (proposals),
// pass the selected proposal/NGO via real navigation params,
// and delete this file.
// ============================================================

data class MockProposal(
    val id: String,
    val ngoName: String,
    val ngoInitials: String,
    val title: String,
    val content: String,   // short summary — doubles as the proposal email body
    val docsLink: String,  // the Google Docs document edited OUTSIDE the app
    val status: String,    // DRAFT / SENT / APPROVED / REJECTED
    val updated: String
)

val mockProposals = listOf(
    MockProposal(
        id = "p1",
        ngoName = "Mount Miriam Cancer Hospital",
        ngoInitials = "MM",
        title = "Subsidised Cancer Treatment Fund — Partnership Proposal",
        content = "Kindly will run a two-month online donation campaign for Mount Miriam's subsidised treatment ward. Donors give in RM and the NGO receives 95% of every confirmed donation. Deliverables: campaign poster set, weekly progress updates and a closing report.",
        docsLink = "https://docs.google.com/document/d/example-mount-miriam",
        status = "APPROVED",
        updated = "10 Nov 2026"
    ),
    MockProposal(
        id = "p2",
        ngoName = "The Habitat Foundation",
        ngoInitials = "HF",
        title = "Rainforest Restoration Programme — Partnership Proposal",
        content = "A three-week campaign supporting native tree replanting. Kindly handles donation collection and progress tracking; the Foundation provides site updates and photos for the social feed.",
        docsLink = "https://docs.google.com/document/d/example-habitat",
        status = "SENT",
        updated = "14 Nov 2026"
    ),
    MockProposal(
        id = "p3",
        ngoName = "Kawan",
        ngoInitials = "KW",
        title = "Community Meals for the Needy — Partnership Proposal",
        content = "Weekend meal-packaging drives in George Town funded through Kindly donations.",
        docsLink = "",
        status = "DRAFT",
        updated = "18 Nov 2026"
    ),
    MockProposal(
        id = "p4",
        ngoName = "St. Nicholas Home, Penang",
        ngoInitials = "SN",
        title = "Assistive Technology Classroom — Partnership Proposal",
        content = "Equip a classroom with braille displays and screen readers, funded by a month-long Kindly campaign.",
        docsLink = "https://docs.google.com/document/d/example-st-nicholas",
        status = "REJECTED",
        updated = "2 Nov 2026"
    )
)

val mockProposalNgos = listOf(
    "Mount Miriam Cancer Hospital",
    "The Habitat Foundation",
    "Kawan",
    "St. Nicholas Home, Penang",
    "Women's Centre for Change (WCC) Penang"
)

// Temporary UI-only handoff so ProposalsScreen can open the editor
// with a proposal loaded (real navigation params arrive with the backend).
var editorDraft by mutableStateOf<MockProposal?>(null)
