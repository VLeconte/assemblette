import { defineStore } from 'pinia'
import _ from 'lodash'
import type { Vote } from '@/entities/vote'
import axios from 'axios'

export const useVotesStore = defineStore('votes', () => {
  const restVotes = axios.create({
    // better still, use env vars to define your URLs
    baseURL: 'http://localhost:8080/api/votes',
  })
  const votes: Vote[] = []

  async function getVotes(): Promise<Vote[]> {
    try {
      if (_.isEmpty(votes)) {
        return (await restVotes.get('')).data
      }
      return votes
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        console.error(err.toJSON())
        throw new Error(err.message)
      } else {
        console.error(err)
        throw new Error('An unexpected error occurred')
      }
    }
  }

  return { getVotes }
})
