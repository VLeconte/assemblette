import axios from 'axios'

const restVotes = axios.create({
  // better still, use env vars to define your URLs
  baseURL: 'http://localhost:8080/api/votes',
})

export default class VotesService {
  public getVotes = async () => {
    try {
      return (await restVotes.get('')).data
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
}
